package com.kenshoo.pl.intellij.codegen.scala;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.codegen.ClassCreator;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ScalaClassCreator implements ClassCreator {

    public static ScalaClassCreator INSTANCE = new ScalaClassCreator();

    @Override
    public void generateClass(PsiDirectory directory, String className, String code) {
        // TODO
    }
}
