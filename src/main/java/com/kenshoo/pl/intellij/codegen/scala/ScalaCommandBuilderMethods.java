package com.kenshoo.pl.intellij.codegen.scala;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaCommandBuilderMethods {

    private final String commandType;
    private final String entityTypeName;

    public ScalaCommandBuilderMethods(final String commandType, final String entityClass) {
        this.commandType = commandType;
        this.entityTypeName = entityClass;
    }

    public String build() {
        return Stream.<String>builder()
            .add("def add[T](field: EntityField[" + entityTypeName + ", T], value: T): " + commandType + " = { ")
            .add("set(field, value)")
            .add("this")
            .add("}")
            .add("")

            // --- add child -- //

            .add("def add[T, C <: EntityType[C]](childCommand: ChangeEntityCommand[C]): " + commandType + " = { ")
            .add("addChild(childCommand)")
            .add("this")
            .add("}")
            .add("")

            // --- add missing children supplier -- //

            .add("def addSupplier(missingChildrenSupplier: MissingChildrenSupplier[_ <: EntityType[_]]): " + commandType + " = { ")
            .add("add(missingChildrenSupplier)")
            .add("this")
            .add("}")

            .build()
            .collect(joining("\n"));
    }
}
