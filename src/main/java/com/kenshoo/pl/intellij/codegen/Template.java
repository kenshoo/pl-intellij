package com.kenshoo.pl.intellij.codegen;

import com.intellij.ide.fileTemplates.FileTemplate;

import java.util.Map;

public class Template {
    private String className;
    private String templateName;
    private Map<String, String> parameters;

    public Template(String className, String templateName, Map<String, String> parameters) {
        this.className = className;
        this.templateName = templateName;
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
