package com.kenshoo.pl.intellij.codegen;

import com.kenshoo.pl.intellij.model.EntityInput;

public interface EntityCodeGenerator {

    String generate(String entityTypeName, String tableTypeName, EntityInput input);
}
