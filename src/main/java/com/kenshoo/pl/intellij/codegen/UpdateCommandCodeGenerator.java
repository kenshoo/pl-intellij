package com.kenshoo.pl.intellij.codegen;

public interface UpdateCommandCodeGenerator {
    String generate(String commandClass, String entityClassName);
}
