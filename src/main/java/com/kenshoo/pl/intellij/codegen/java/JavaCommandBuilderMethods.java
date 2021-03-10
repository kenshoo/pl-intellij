package com.kenshoo.pl.intellij.codegen.java;

public class JavaCommandBuilderMethods {

    private static final String NEW_LINE = "\n";

    private final String commandClass;
    private final String entityClassName;

    public JavaCommandBuilderMethods(String commandClass, String entityClass) {
        this.commandClass = commandClass;
        this.entityClassName = entityClass;
    }

    public String build() {
        return new StringBuilder()
                .append("public <T>" ).append(commandClass).append(" with(EntityField<").append(entityClassName) .append(", T> field, T value) { ")
                .append(NEW_LINE)
                .append("set(field, value);")
                .append(NEW_LINE)
                .append("return this;")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                // --- with child -- //

                .append(NEW_LINE)
                .append("public <T>" ).append(commandClass).append(" with(ChangeEntityCommand<? extends EntityType> childCommand) {")
                .append(NEW_LINE)
                .append("addChild(childCommand);")
                .append(NEW_LINE)
                .append("return this;")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                // --- with deletion -- //

                .append(NEW_LINE)
                .append("public <T>" ).append(commandClass).append(" with(MissingChildrenSupplier<? extends EntityType> missingChildrenSupplier) {")
                .append(NEW_LINE)
                .append("this.add(missingChildrenSupplier);")
                .append(NEW_LINE)
                .append("return this;")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .toString();
    }
}
