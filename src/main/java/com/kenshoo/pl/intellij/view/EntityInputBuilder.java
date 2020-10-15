package com.kenshoo.pl.intellij.view;

import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;
import com.kenshoo.pl.intellij.model.FieldFlags;
import com.kenshoo.pl.intellij.model.FieldType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kenshoo.pl.intellij.view.NewEntityForm.*;

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
        this.fields = IntStream.range(0, fields.getRowCount())
                .mapToObj(rowIndex -> createEntitySchemaField(fields, rowIndex))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return this;
    }

    public EntityInput build() {
        return new EntityInput(entityName, tableName, fields);
    }

    @NotNull
    private Optional<EntitySchemaField> createEntitySchemaField(JTable fields, int rowIndex) {
        return Optional.ofNullable(fields.getValueAt(rowIndex, FIELD_NAME_COL))
                .map(nameCell -> {
                    final String fieldName = nameCell.toString();
                    final FieldType type = (FieldType) fields.getValueAt(rowIndex, FIELD_TYPE_COL);
                    final FieldFlags flags = (FieldFlags) fields.getValueAt(rowIndex, FIELD_FLAGS_COL);
                    return Optional.of(new EntitySchemaField(fieldName, type, flags));
                })
                .orElseGet(Optional::empty);
    }
}
