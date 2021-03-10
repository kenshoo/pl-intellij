package com.kenshoo.pl.intellij.codegen;

public interface DeleteCommandCodeGenerator {
    String generate(String commandClass, String entityClassName);
}
