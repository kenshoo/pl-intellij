package com.kenshoo.pl.intellij.codegen;

import com.kenshoo.pl.intellij.model.FieldFlags;

public class FieldFlagsCodeGenerator {

    public static final FieldFlagsCodeGenerator INSTANCE = new FieldFlagsCodeGenerator();

    private FieldFlagsCodeGenerator() {
        //singleton
    }

    public String autoInc(FieldFlags flags) {
        return flags.isAutoInc() ? ".identity(true)" : "";
    }

    public String functionNameOf(FieldFlags flags) {
        return flags.isPk() ? "createPKField" : "createField";
    }
}
