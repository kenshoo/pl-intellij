package com.kenshoo.pl.intellij.codegen;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.util.ArrayUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

public class TemplateGenerator {

    public static TemplateGenerator INSTANCE = new TemplateGenerator();

    private static final String TEMPLATE_MANE = "Code";
    private static final String TEMPLATE_CONTENT = "${CODE}";

    public TemplateGenerator() {
        Stream.of(ProjectManager.getInstance().getOpenProjects()).forEach(project -> {
            final FileTemplateManager templateManager = FileTemplateManager.getInstance(project);
            final FileTemplate[] templates = templateManager.getAllTemplates();
            if (Stream.of(templates).noneMatch(template -> template.getName().equals(TEMPLATE_MANE))) {
                final FileTemplate template = FileTemplateUtil.createTemplate(TEMPLATE_MANE, "java", TEMPLATE_CONTENT, templates);
                templateManager.setTemplates(FileTemplateManager.DEFAULT_TEMPLATES_CATEGORY, Arrays.asList(ArrayUtil.append(templates, template)));
            }
        });
    }

    public void generateClass(PsiJavaDirectoryImpl directory, String className, String code) {
        final Map<String, String> templateParameters = Collections.singletonMap("CODE", code);
        JavaDirectoryService.getInstance().createClass(directory, className, TEMPLATE_MANE, false, templateParameters);
    }
}
