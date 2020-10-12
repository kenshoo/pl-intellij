package com.kenshoo.pl.intellij.entities;

import java.util.List;

public class EntityInput {
    private final String entityName;
    private final String tableName;
    private final List<EntitySchemaField> fields;

    public EntityInput(String entityName, String tableName, List<EntitySchemaField> fields) {
        this.entityName = entityName;
        this.tableName = tableName;
        this.fields = fields;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<EntitySchemaField> getFields() {
        return fields;
    }
}
