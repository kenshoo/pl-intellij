package com.kenshoo.pl.intellij.model;


public class FieldType {
    private final String sqlType;
    private final Class javaType;

    public FieldType(String sqlType, Class javaType) {
        this.sqlType = sqlType;
        this.javaType = javaType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public Class getJavaType() {
        return javaType;
    }

    @Override
    public String toString() {
        return sqlType;
    }
}
