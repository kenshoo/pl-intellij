package com.kenshoo.pl.intellij.codegen.java;

import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.codegen.FieldFlagsCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;

import java.util.List;
import java.util.stream.Collectors;

public class JavaEntityCodeGenerator implements EntityCodeGenerator {

    public static final JavaEntityCodeGenerator INSTANCE = new JavaEntityCodeGenerator();

    private final FieldFlagsCodeGenerator fieldFlagsGenerator = FieldFlagsCodeGenerator.INSTANCE;

    private static final String NEW_LINE = "\n";

    public String generate(String entityClassName, String tableClassName, EntityInput input) {
        return new StringBuilder()
                .append("import com.kenshoo.pl.entity.AbstractEntityType;")
                .append(NEW_LINE)
                .append("import com.kenshoo.pl.entity.EntityField;")
                .append(NEW_LINE)
                .append("import com.kenshoo.pl.entity.annotation.Immutable;")
                .append(NEW_LINE)
                .append("import com.kenshoo.jooq.DataTable;")
                .append(NEW_LINE)
                .append("import java.sql.Timestamp;")
                .append(NEW_LINE)
                .append("public class ").append(entityClassName).append(" extends AbstractEntityType<").append(entityClassName).append("> {")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public static final ").append(entityClassName).append(" INSTANCE = new ").append(entityClassName).append("();")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(createFields(input.getFields(), entityClassName, tableClassName))
                .append(NEW_LINE)
                .append("private ").append(entityClassName).append("() {")
                .append(NEW_LINE)
                .append("super(\"").append(input.getTableName()).append("\");")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("@Override")
                .append(NEW_LINE)
                .append("public DataTable getPrimaryTable() {")
                .append(NEW_LINE)
                .append("return ").append(tableClassName).append(".TABLE;")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .append("}")
                .toString();
    }

    private String createFields(List<EntitySchemaField> fields, String entityClassName, String tableClassName) {
        return fields.stream()
                .map(field -> new StringBuilder()
                        .append(fieldFlagsGenerator.immutableAnnotation(field.getFlags()))
                        .append(NEW_LINE)
                        .append("public static final EntityField<")
                        .append(entityClassName)
                        .append(", ")
                        .append(field.getType().getJavaType().getSimpleName())
                        .append("> ")
                        .append(field.getFieldName().toUpperCase())
                        .append(" = INSTANCE.field(")
                        .append(tableClassName).append(".TABLE.")
                        .append(field.getFieldName())
                        .append(");")
                        .toString())
                .collect(Collectors.joining(NEW_LINE));
    }
}
