package com.kenshoo.pl.intellij.controller;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.codegen.*;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;


public class NewEntityController {

    public final static NewEntityController INSTANCE = new NewEntityController();

    private final ClassCreator classCreator;
    private final TableCodeGenerator tableCodeGenerator;
    private final EntityCodeGenerator entityCodeGenerator;
    private final EntityUniqueKeyCodeGenerator uniqueKeyCodeGenerator;
    private final EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;
    private final CreateCommandCodeGenerator createCommandCodeGenerator;

    public NewEntityController() {
        this.classCreator = ClassCreator.INSTANCE;
        this.tableCodeGenerator = TableCodeGenerator.INSTANCE;
        this.entityCodeGenerator = EntityCodeGenerator.INSTANCE;
        this.uniqueKeyCodeGenerator = EntityUniqueKeyCodeGenerator.INSTANCE;
        this.entityPersistenceCodeGenerator = EntityPersistenceCodeGenerator.INSTANCE;
        this.createCommandCodeGenerator = CreateCommandCodeGenerator.INSTANCE;
    }

    public NewEntityController(
            ClassCreator classCreator,
            TableCodeGenerator tableCodeGenerator,
            EntityCodeGenerator entityCodeGenerator,
            EntityUniqueKeyCodeGenerator uniqueKeyCodeGenerator,
            EntityPersistenceCodeGenerator entityPersistenceCodeGenerator,
            CreateCommandCodeGenerator createCommandCodeGenerator
            ) {
        this.classCreator = classCreator;
        this.tableCodeGenerator = tableCodeGenerator;
        this.entityCodeGenerator = entityCodeGenerator;
        this.uniqueKeyCodeGenerator = uniqueKeyCodeGenerator;
        this.entityPersistenceCodeGenerator = entityPersistenceCodeGenerator;
        this.createCommandCodeGenerator = createCommandCodeGenerator;
    }

    public void createNewEntity(PsiJavaDirectoryImpl directory, EntityInput input) {
        final String tableClassName = createTableClassName(input.getTableName());
        final String entityClassName = createEntityClassName(input.getEntityName());
        final String uniqueKeyClassName = createUniqueKeyClassName(input.getEntityName(), pkField(input).getFieldName());
        final String entityPersistenceClassName = createEntityPersistenceClassName(input.getEntityName());
        final String createCommandClassName = "Create" + input.getEntityName();

        final String tableCode = tableCodeGenerator.generate(tableClassName, input);
        final String entityCode = entityCodeGenerator.generate(entityClassName, tableClassName, input);
        final String uniqueKeyCode = uniqueKeyCodeGenerator.generate(entityClassName, uniqueKeyClassName, pkField(input));
        final String entityPersistenceCode = entityPersistenceCodeGenerator.generate(entityClassName, entityPersistenceClassName);
        final String createCommandCode = createCommandCodeGenerator.generate(createCommandClassName, entityClassName);

        classCreator.generateClass(directory, tableClassName, tableCode);
        classCreator.generateClass(directory, entityClassName, entityCode);
        classCreator.generateClass(directory, uniqueKeyClassName, uniqueKeyCode);
        classCreator.generateClass(directory, entityPersistenceClassName, entityPersistenceCode);
        classCreator.generateClass(directory, createCommandCode, createCommandCode);
    }

    @VisibleForTesting
    String createTableClassName(String tableName) {
        return String.format("%sTable", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
    }

    @VisibleForTesting
    String createEntityClassName(String entityName) {
        return String.format("%sEntity", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

    @VisibleForTesting
    String createEntityPersistenceClassName(String entityName) {
        return String.format("%sPersistence", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

    @VisibleForTesting
    String createUniqueKeyClassName(String entityName, String fieldName) {
        return String.format("%s%s",
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName),
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, fieldName)
        );
    }

    private EntitySchemaField pkField(EntityInput input) {
        return input.getFields().stream().filter(f -> f.getFlags().isPk()).findFirst().get();
    }

}
