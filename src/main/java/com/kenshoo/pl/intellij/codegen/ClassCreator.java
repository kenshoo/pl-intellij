package com.kenshoo.pl.intellij.codegen;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ClassCreator {

    public static ClassCreator INSTANCE = new ClassCreator();

    private static final String TEMPLATE_NAME = "PL Code Generator (internal)";
    private static final String TEMPLATE_CONTENT = "${CODE}";

    public void generateClass(PsiDirectory directory, String className, String code) {
        final FileTemplate template = getOrCreateTemplate(directory.getProject());
        final Map<String, String> templateParameters = Collections.singletonMap("CODE", code);
        JavaDirectoryService.getInstance().createClass(directory, className, template.getName(), false, templateParameters);
    }


    private FileTemplate getOrCreateTemplate(Project project) {
            final FileTemplateManager templateManager = FileTemplateManager.getInstance(project);
            return Optional.ofNullable(templateManager.getTemplate(TEMPLATE_NAME))
                    .orElseGet(()-> createTemplate(templateManager));
    }

    private FileTemplate createTemplate(FileTemplateManager templateManager) {
        final FileTemplate template = templateManager.addTemplate(TEMPLATE_NAME,"java");
        template.setText(TEMPLATE_CONTENT);
        return template;
    }
}
