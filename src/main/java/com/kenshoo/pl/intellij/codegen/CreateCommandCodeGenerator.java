package com.kenshoo.pl.intellij.codegen;

public class CreateCommandCodeGenerator {

    public static final CreateCommandCodeGenerator INSTANCE = new CreateCommandCodeGenerator();

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

                // --- with field -- //

                .append(NEW_LINE)
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

                .append(NEW_LINE)
                .append("}")
                .toString();
    }


}
