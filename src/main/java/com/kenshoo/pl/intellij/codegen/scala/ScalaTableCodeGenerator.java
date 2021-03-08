package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.FieldFlagsCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ScalaTableCodeGenerator implements TableCodeGenerator {

    public static final ScalaTableCodeGenerator INSTANCE = new ScalaTableCodeGenerator();

    private final FieldFlagsCodeGenerator fieldFlagsGenerator = FieldFlagsCodeGenerator.INSTANCE;

    public String generate(final String typeName, final EntityInput input) {
        return Stream.<String>builder()
            .add("import com.kenshoo.jooq.AbstractDataTable")
            .add("import org.jooq.impl.SQLDataType")
            .add("")
            .add("//scalastyle:off")
            .add("class " + typeName + " private(name: String, aliased: " + typeName + " = null)")
            .add("extends AbstractDataTable[" + typeName + "](aliased, name) {")
            .add("")
            .add(createFields(input.getFields()))
            .add("")
            .add("def this() {")
            .add("this(\"" + input.getTableName() + "\")")
            .add("}")
            .add("")
            .add("override def as(alias: String) = new " + typeName + "(alias, this)")
            .add("}")
            .add("//scalastyle:on")
            .add("")
            .add("object " + typeName + "{")
            .add("val TABLE = new " + typeName)
            .add("}")
            .build()
            .collect(joining("\n"));
    }

    private String createFields(final List<EntitySchemaField> fields) {
        return fields.stream()
                     .map(this::createField)
                     .collect(joining("\n"));
    }

    private String createField(final EntitySchemaField field) {
        return "final val " + field.getFieldName()
            + " = "
            + fieldFlagsGenerator.functionNameOf(field.getFlags())
            + "(\"" + field.getFieldName() + "\", "
            + "SQLDataType."
            + field.getType().getSqlType().toUpperCase()
            + fieldFlagsGenerator.autoInc(field.getFlags())
            + ")";
    }
}
