package com.kenshoo.pl.intellij.codegen;

public interface CreateCommandCodeGenerator {
    String generate(String commandClass, String entityClassName);
}
