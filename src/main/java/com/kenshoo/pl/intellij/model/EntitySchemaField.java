package com.kenshoo.pl.intellij.model;

public class EntitySchemaField {
    private final String fieldName;
    private final FieldType type;
    private final FieldFlags flags;

    public EntitySchemaField(String fieldName, FieldType type, FieldFlags flags) {
        this.fieldName = fieldName;
        this.type = type;
        this.flags = flags;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldType getType() {
        return type;
    }

    public FieldFlags getFlags() {
        return flags;
    }
}
