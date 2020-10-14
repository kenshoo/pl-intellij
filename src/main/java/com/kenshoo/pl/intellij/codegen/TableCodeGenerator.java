package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.model.EntityInput;

public class TableCodeGenerator {

    public static final TableCodeGenerator INSTANCE = new TableCodeGenerator();

    public String generate(String className, EntityInput input, PsiJavaDirectoryImpl directory) {
        return new StringBuilder()
                .append("public class ").append(className).append(" {\n")
                .append("}")
                .toString();
    }
}
