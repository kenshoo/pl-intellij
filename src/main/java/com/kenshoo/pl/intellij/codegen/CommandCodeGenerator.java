package com.kenshoo.pl.intellij.codegen;

public interface CommandCodeGenerator {
    String generate(String commandType, String entityTypeName);
}
