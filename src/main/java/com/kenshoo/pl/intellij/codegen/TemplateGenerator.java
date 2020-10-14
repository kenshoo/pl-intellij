package com.kenshoo.pl.intellij.codegen;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.util.ArrayUtil;

import java.util.Arrays;
import java.util.stream.Stream;

public class TemplateGenerator {

    public static TemplateGenerator INSTANCE = new TemplateGenerator();


    public void generate(PsiJavaDirectoryImpl directory, String templateName, String templateContent) {
        FileTemplateManager templateManager = FileTemplateManager.getInstance(directory.getProject());
        FileTemplate[] templates = templateManager.getAllTemplates();
        if(Stream.of(templates).noneMatch(template-> template.getName().equals(templateName))){
            FileTemplate template = FileTemplateUtil.createTemplate(templateName, "java", templateContent, templates);
            templateManager.setTemplates(FileTemplateManager.DEFAULT_TEMPLATES_CATEGORY, Arrays.asList(ArrayUtil.append(templates, template)));
        }
    }
}
