package com.kenshoo.pl.intellij.codegen.java;

import com.kenshoo.pl.intellij.codegen.CreateCommandCodeGenerator;

public class JavaCreateCommandCodeGenerator implements CreateCommandCodeGenerator {

    public static final JavaCreateCommandCodeGenerator INSTANCE = new JavaCreateCommandCodeGenerator();

    private static final String NEW_LINE = "\n";

    public String generate(String commandClass, String entityClassName) {

        return new StringBuilder()
                .append("import com.kenshoo.pl.entity.*;")
                .append(NEW_LINE)
                .append("import com.kenshoo.pl.entity.internal.MissingChildrenSupplier;")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public class ").append(commandClass).append(" extends CreateEntityCommand<").append(entityClassName).append("> {")
                .append(NEW_LINE)

                // --- constructor -- //

                .append(NEW_LINE)
                .append("public ").append(commandClass).append("() {")
                .append(NEW_LINE)
                .append("super(").append(entityClassName).append(".INSTANCE);")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                .append(NEW_LINE)
                .append(new JavaCommandBuilderMethods(commandClass, entityClassName).build())
                .append(NEW_LINE)
                .append("}")
                .toString();
    }


}
