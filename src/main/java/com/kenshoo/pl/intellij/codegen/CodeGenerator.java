package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.model.EntityInput;

public interface CodeGenerator {
    Template generate(PsiJavaDirectoryImpl directory, EntityInput input);
}
