package com.kenshoo.pl.intellij.codegen.scala;


import com.kenshoo.pl.intellij.codegen.CommandCodeGenerator;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaUpsertCommandCodeGenerator implements CommandCodeGenerator {

    public static final ScalaUpsertCommandCodeGenerator INSTANCE = new ScalaUpsertCommandCodeGenerator();

    @Override
    public String generate(String commandType, String entityTypeName) {
        return Stream.<String>builder()
            .add("import com.kenshoo.pl.entity._")
            .add("import com.kenshoo.pl.entity.internal.MissingChildrenSupplier")
            .add("")
            .add("class " + commandType + "(val id: Identifier[" + entityTypeName + "])")
            .add("extends InsertOnDuplicateUpdateCommand[" + entityTypeName + ", Identifier[" + entityTypeName + "]](" + entityTypeName + ".INSTANCE, id) {")
            .add("")
            .add(new ScalaCommandBuilderMethods(commandType, entityTypeName).build())
            .add("}")
            .build()
            .collect(joining("\n"));
    }

}
