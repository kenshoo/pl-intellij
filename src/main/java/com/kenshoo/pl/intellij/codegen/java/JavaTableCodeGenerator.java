package com.kenshoo.pl.intellij.codegen.java;

import com.kenshoo.pl.intellij.codegen.FieldFlagsCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;

import java.util.List;
import java.util.stream.Collectors;

public class JavaTableCodeGenerator implements TableCodeGenerator {

    public static final JavaTableCodeGenerator INSTANCE = new JavaTableCodeGenerator();

    private final FieldFlagsCodeGenerator fieldFlagsGenerator = FieldFlagsCodeGenerator.INSTANCE;

    private static final String NEW_LINE = "\n";

    public String generate(String className, EntityInput input) {
        return new StringBuilder()
                .append("import com.kenshoo.jooq.AbstractDataTable;")
                .append(NEW_LINE)
                .append("import org.jooq.TableField;")
                .append(NEW_LINE)
                .append("import org.jooq.Record;")
                .append(NEW_LINE)
                .append("import org.jooq.impl.SQLDataType;")
                .append(NEW_LINE)
                .append("import java.sql.Timestamp;")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public class ").append(className).append(" extends AbstractDataTable<").append(className).append("> {")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public static final ").append(className).append(" TABLE = new ").append(className).append("();")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append(createFields(input.getFields()))
                .append(NEW_LINE)
                .append("private ").append(className).append("() {")
                .append(NEW_LINE)
                .append("super(\"").append(input.getTableName()).append("\");")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .append("private ").append(className).append("(").append(className).append(" aliased, String alias) {")
                .append(NEW_LINE)
                .append("super(aliased, alias);")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .append("@Override")
                .append(NEW_LINE)
                .append("public ").append(className).append(" as(String alias) {")
                .append(NEW_LINE)
                .append("return new ").append(className).append("(this, alias);")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)
                .append("}")
                .toString();
    }

    private String createFields(List<EntitySchemaField> fields) {
        return fields.stream()
                .map(field -> new StringBuilder()
                        .append("public final TableField<Record, ")
                        .append(field.getType().getJavaType().getSimpleName())
                        .append("> ")
                        .append(field.getFieldName())
                        .append(" = ")
                        .append(fieldFlagsGenerator.functionNameOf(field.getFlags()))
                        .append("(\"").append(field.getFieldName()).append("\", ")
                        .append("SQLDataType.")
                        .append(field.getType().getSqlType().toUpperCase())
                        .append(fieldFlagsGenerator.autoInc(field.getFlags()))
                        .append(");")
                        .toString())
                .collect(Collectors.joining(NEW_LINE));
    }
}
