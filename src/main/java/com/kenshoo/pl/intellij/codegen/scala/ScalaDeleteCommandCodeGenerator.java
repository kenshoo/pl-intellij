package com.kenshoo.pl.intellij.codegen.scala;


import com.kenshoo.pl.intellij.codegen.CommandCodeGenerator;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaDeleteCommandCodeGenerator implements CommandCodeGenerator {

    public static final ScalaDeleteCommandCodeGenerator INSTANCE = new ScalaDeleteCommandCodeGenerator();

    public String generate(String commandType, String entityTypeName) {
        return Stream.<String>builder()
            .add("import com.kenshoo.pl.entity._")
            .add("import com.kenshoo.pl.entity.internal.MissingChildrenSupplier")
            .add("")
            .add("class " + commandType + "(val id: Identifier[" + entityTypeName + "])")
            .add("extends DeleteEntityCommand[" + entityTypeName + ", Identifier[" + entityTypeName + "]](" + entityTypeName + ".INSTANCE, id) {")
            .add("")
            .add(new ScalaCommandBuilderMethods(commandType, entityTypeName).build())
            .add("}")
            .build()
            .collect(joining("\n"));
    }
}
