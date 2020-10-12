package com.kenshoo.pl.intellij.model;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityInputBuilder {
    private String entityName;
    private String tableName;
    private List<EntitySchemaField> fields;


    public EntityInputBuilder withEntityName(JTextField entityName) {
        this.entityName = entityName.getText();
        return this;
    }

    public EntityInputBuilder withTableName(JTextField tableName) {
        this.tableName = tableName.getText();
        return this;
    }

    public EntityInputBuilder withFields(JTable fields) {
        this.fields = IntStream.range(0, fields.getSelectedRowCount())
                .mapToObj(rowIndex -> createEntitySchemaField(fields, rowIndex))
                .collect(Collectors.toList());
        return this;
    }

    public EntityInput build() {
        return new EntityInput(entityName, tableName, fields);
    }

    @NotNull
    private EntitySchemaField createEntitySchemaField(JTable fields, int rowIndex) {
        final String fieldName = fields.getValueAt(rowIndex, 0).toString();
        final FieldType type = (FieldType) fields.getValueAt(rowIndex, 1);
        final int size = Integer.parseInt(fields.getValueAt(rowIndex, 2).toString());
        final FieldFlags flags = (FieldFlags) fields.getValueAt(rowIndex, 3);
        return new EntitySchemaField(fieldName, type, size, flags);
    }
}
