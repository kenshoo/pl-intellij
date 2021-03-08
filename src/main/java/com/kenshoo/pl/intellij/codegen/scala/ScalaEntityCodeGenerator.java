package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.codegen.FieldFlagsCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaEntityCodeGenerator implements EntityCodeGenerator {

    public static final ScalaEntityCodeGenerator INSTANCE = new ScalaEntityCodeGenerator();

    private final FieldFlagsCodeGenerator fieldFlagsGenerator = FieldFlagsCodeGenerator.INSTANCE;

    public String generate(final String entityTypeName, final String tableTypeName, final EntityInput input) {
        return Stream.<String>builder()
            .add("import com.kenshoo.pl.entity.AbstractEntityType")
            .add("import com.kenshoo.pl.entity.EntityField")
            .add("import com.kenshoo.pl.entity.annotation.Immutable")
            .add("import com.kenshoo.jooq.DataTable")
            .add("class " + entityTypeName + " private extends AbstractEntityType[" + entityTypeName + "](\"" + input.getTableName() + "\") {")
            .add("")
            .add(createFields(input.getFields(), entityTypeName, tableTypeName))
            .add("")
            .add("override def getPrimaryTable: DataTable = " + tableTypeName + ".TABLE")
            .add("")
            .add("}")
            .add("object " + entityTypeName + " {")
            .add("val INSTANCE = new " + entityTypeName)
            .add("}")
            .build()
            .collect(joining("\n"));
    }

    private String createFields(final List<EntitySchemaField> fields, final String entityTypeName, final String tableTypeName) {
        return fields.stream()
                     .map(field -> createField(field, entityTypeName, tableTypeName))
                     .collect(joining("\n"));
    }

    private String createField(final EntitySchemaField field, final String entityTypeName, final String tableTypeName) {
        return fieldFlagsGenerator.immutableAnnotation(field.getFlags()) + "\n"
            + "val " + field.getFieldName().toUpperCase() + ": "
            + "EntityField[" + entityTypeName + ", " + field.getType().getJavaType().getCanonicalName() + "]"
            + " = field(" + tableTypeName + ".TABLE." + field.getFieldName() + ")";
    }
}
