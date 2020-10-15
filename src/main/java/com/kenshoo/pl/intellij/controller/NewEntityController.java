package com.kenshoo.pl.intellij.controller;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.codegen.ClassCreator;
import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.codegen.EntityUniqueKeyCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;


public class NewEntityController {

    public final static NewEntityController INSTANCE = new NewEntityController();

    private final ClassCreator classCreator;
    private final TableCodeGenerator tableCodeGenerator;
    private final EntityCodeGenerator entityCodeGenerator;
    private final EntityUniqueKeyCodeGenerator uniqueKeyCodeGenerator;

    public NewEntityController() {
        this.classCreator = ClassCreator.INSTANCE;
        this.tableCodeGenerator = TableCodeGenerator.INSTANCE;
        this.entityCodeGenerator = EntityCodeGenerator.INSTANCE;
        this.uniqueKeyCodeGenerator = EntityUniqueKeyCodeGenerator.INSTANCE;
    }

    public NewEntityController(
            ClassCreator classCreator,
            TableCodeGenerator tableCodeGenerator,
            EntityCodeGenerator entityCodeGenerator,
            EntityUniqueKeyCodeGenerator uniqueKeyCodeGenerator
            ) {
        this.classCreator = classCreator;
        this.tableCodeGenerator = tableCodeGenerator;
        this.entityCodeGenerator = entityCodeGenerator;
        this.uniqueKeyCodeGenerator = uniqueKeyCodeGenerator;
    }

    public void createNewEntity(PsiJavaDirectoryImpl directory, EntityInput input) {
        final String tableClassName = createTableClassName(input.getTableName());
        final String entityClassName = createEntityClassName(input.getEntityName());
        final String uniqueKeyClassName = createUniqueKeyClassName(input.getEntityName(), pkField(input).getFieldName());

        final String tableCode = tableCodeGenerator.generate(tableClassName, input);
        final String entityCode = entityCodeGenerator.generate(entityClassName, tableClassName, input);
        final String uniqueKeyCode = uniqueKeyCodeGenerator.generate(entityClassName, uniqueKeyClassName, pkField(input));

        classCreator.generateClass(directory, tableClassName, tableCode);
        classCreator.generateClass(directory, entityClassName, entityCode);
        classCreator.generateClass(directory, uniqueKeyClassName, uniqueKeyCode);
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
