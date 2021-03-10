package com.kenshoo.pl.intellij.codegen.scala;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.kenshoo.pl.intellij.codegen.SourceCodeFilePersister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.scala.ScalaFileType;
import org.jetbrains.plugins.scala.lang.refactoring.util.ScalaNamesUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static com.intellij.openapi.command.WriteCommandAction.writeCommandAction;

public class ScalaSourceFilePersister implements SourceCodeFilePersister {

    public static ScalaSourceFilePersister INSTANCE = new ScalaSourceFilePersister();

    private static final String TEMPLATE_NAME = "PL Scala Code Generator (internal)";
    private static final String TEMPLATE_CONTENT =
        "#if ((${PACKAGE_NAME} && ${PACKAGE_NAME} != \"\"))package ${PACKAGE_NAME} #end\n\n" +
            "${CODE}";

    @Override
    public void persist(@NotNull PsiDirectory directory,
                        @NotNull String typeName,
                        @NotNull String code) {
        final Project project = directory.getProject();
        writeCommandAction(project).run(() -> persistInner(project,
                                                           directory,
                                                           typeName,
                                                           code));
    }

    private void persistInner(final Project project,
                              final PsiDirectory directory,
                              final String typeName,
                              final String code) {
        final FileTemplateManager templateManager = FileTemplateManager.getInstance(project);
        final FileTemplate template = getOrCreateTemplate(templateManager);
        final Properties properties = new Properties(FileTemplateManager.getInstance(project).getDefaultProperties());
        properties.setProperty(FileTemplate.ATTRIBUTE_PACKAGE_NAME,
                               ScalaNamesUtil.escapeKeywordsFqn(JavaTemplateUtil.getPackageName(directory)));
        properties.setProperty("CODE", code);
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
        final String fileName = String.format("%s.%s", typeName, ScalaFileType.INSTANCE.getDefaultExtension());
        final String text = applyTemplate(templateManager, template, properties);

        final PsiFile file = fileFactory.createFileFromText(fileName, ScalaFileType.INSTANCE, text);
        CodeStyleManager.getInstance(project).reformat(file);
        directory.add(file);
    }

    private FileTemplate getOrCreateTemplate(FileTemplateManager templateManager) {
        return Optional.ofNullable(templateManager.getTemplate(TEMPLATE_NAME))
                       .orElseGet(() -> createTemplate(templateManager));
    }

    private FileTemplate createTemplate(FileTemplateManager templateManager) {
        final FileTemplate template = templateManager.addTemplate(TEMPLATE_NAME, ScalaFileType.INSTANCE.getDefaultExtension());
        template.setText(TEMPLATE_CONTENT);
        return template;
    }

    private String applyTemplate(final FileTemplateManager templateManager,
                                 final FileTemplate template,
                                 final Properties properties) {
        try {
            return template.getText(properties);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to load template for %s",
                                                     templateManager.internalTemplateToSubject(TEMPLATE_NAME)),
                                       e);
        }
    }
}
