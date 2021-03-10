package com.kenshoo.pl.intellij.codegen;

public interface UpsertCommandCodeGenerator {
    String generate(String commandClass, String entityClassName);
}
