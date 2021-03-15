package com.kenshoo.pl.intellij.codegen;

public interface EntityPersistenceCodeGenerator {
    String generate(String entityTypeName, String entityPersistenceTypeName);
}
