package com.kenshoo.pl.intellij.codegen;

import com.kenshoo.pl.intellij.model.EntitySchemaField;

public class EntityUniqueKeyCodeGenerator {

    public static final EntityUniqueKeyCodeGenerator INSTANCE = new EntityUniqueKeyCodeGenerator();

    private static final String NEW_LINE = "\n";

    public String generate(String entityClassName, String keyClassName, EntitySchemaField pkField) {

        final String pkFieldType = pkField.getType().getJavaType().getSimpleName();

        return new StringBuilder()
                .append("import com.kenshoo.pl.entity.*;")
                .append(NEW_LINE)
                .append("public class ").append(keyClassName).append(" extends SingleUniqueKeyValue<").append(entityClassName).append(", ").append(pkFieldType).append("> {")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("  public ").append(keyClassName).append("(EntityField<").append(entityClassName).append(", ").append(pkFieldType).append("> field, ").append(pkFieldType).append(" value) {")
                .append(NEW_LINE)
                .append("    super(field, value);")
                .append(NEW_LINE)
                .append("  }")
                .append(NEW_LINE)
                .append("}")
                .toString();
    }

}
