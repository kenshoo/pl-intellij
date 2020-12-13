package com.kenshoo.pl.intellij.codegen;


public class UpsertCommandCodeGenerator {

    public static final UpsertCommandCodeGenerator INSTANCE = new UpsertCommandCodeGenerator();

    private static final String NEW_LINE = "\n";

    public String generate(String commandClass, String entityClassName) {

        return new StringBuilder()
                .append("import com.kenshoo.pl.entity.*;")
                .append(NEW_LINE)
                .append("import com.kenshoo.pl.entity.internal.MissingChildrenSupplier;")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public class ").append(commandClass).append(" extends InsertOnDuplicateUpdateCommand<").append(entityClassName).append(", Identifier<").append(entityClassName).append(">> {")
                .append(NEW_LINE)

                // --- constructor -- //

                .append(NEW_LINE)
                .append("public ").append(commandClass).append("(Identifier<").append(entityClassName).append("> id) {")
                .append(NEW_LINE)
                .append("super(").append(entityClassName).append(".INSTANCE, id);")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                .append(NEW_LINE)
                .append(new CommandBuilderMethods(commandClass, entityClassName).build())
                .append(NEW_LINE)
                .append("}")
                .toString();
    }

}
