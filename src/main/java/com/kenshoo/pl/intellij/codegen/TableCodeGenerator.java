package com.kenshoo.pl.intellij.codegen;

import com.kenshoo.pl.intellij.model.EntityInput;

public interface TableCodeGenerator {
    String generate(String typeName, EntityInput input);
}
