package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.CommandCodeGenerator;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaCreateCommandCodeGenerator implements CommandCodeGenerator {

    public static final ScalaCreateCommandCodeGenerator INSTANCE = new ScalaCreateCommandCodeGenerator();

    public String generate(String commandType, String entityTypeName) {

        return Stream.<String>builder()
            .add("import com.kenshoo.pl.entity._")
            .add("import com.kenshoo.pl.entity.internal.MissingChildrenSupplier")
            .add("")
            .add("class " + commandType + " extends CreateEntityCommand[" + entityTypeName + "](" + entityTypeName + ".INSTANCE) {")
            .add("")
            .add(new ScalaCommandBuilderMethods(commandType, entityTypeName).build())
            .add("}")
            .build()
            .collect(joining("\n"));
    }
}
