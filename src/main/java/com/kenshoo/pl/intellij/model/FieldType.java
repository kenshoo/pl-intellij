package com.kenshoo.pl.intellij.model;

import org.jooq.DataType;

public class FieldType {
    private final String sqlType;
    private final Class javaType;

    public FieldType(DataType type) {
        this.sqlType = type.getTypeName();
        this.javaType = type.getType();
    }

    @Override
    public String toString() {
        return sqlType;
    }
}