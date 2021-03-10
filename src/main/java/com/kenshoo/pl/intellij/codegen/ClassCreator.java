package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.PsiDirectory;

public interface ClassCreator {
    void generateClass(PsiDirectory directory, String className, String code);
}
