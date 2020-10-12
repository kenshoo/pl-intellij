package com.kenshoo.pl.intellij.entities;

import com.kenshoo.pl.intellij.GalForm;

public class EntitySchemaField {
    private final String fieldName;
    private final FieldType type;
    private final int size;
    private final FieldFlags flags;

    public EntitySchemaField(String fieldName, FieldType type, int size, FieldFlags flags) {
        this.fieldName = fieldName;
        this.type = type;
        this.size = size;
        this.flags = flags;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public FieldFlags getFlags() {
        return flags;
    }
}
