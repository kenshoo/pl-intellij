package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.PsiDirectory;

public interface TypeCreator {
    void generateType(PsiDirectory directory, String typeName, String code);
}
