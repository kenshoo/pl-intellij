package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.PsiDirectory;

public interface SourceCodeFilePersister {
    void persist(PsiDirectory directory, String typeName, String code);
}
