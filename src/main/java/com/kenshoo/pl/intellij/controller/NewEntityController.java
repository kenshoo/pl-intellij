package com.kenshoo.pl.intellij.controller;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.codegen.ClassCreator;
import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class NewEntityController {

    public final static NewEntityController INSTANCE = new NewEntityController();

    private final ClassCreator classCreator;
    private final TableCodeGenerator tableCodeGenerator;
    private final EntityCodeGenerator entityCodeGenerator;


    public NewEntityController() {
        this.classCreator = ClassCreator.INSTANCE;
        this.tableCodeGenerator = TableCodeGenerator.INSTANCE;
        this.entityCodeGenerator = EntityCodeGenerator.INSTANCE;
    }

    public NewEntityController(ClassCreator classCreator, TableCodeGenerator tableCodeGenerator, EntityCodeGenerator entityCodeGenerator) {
        this.classCreator = classCreator;
        this.tableCodeGenerator = tableCodeGenerator;
        this.entityCodeGenerator = entityCodeGenerator;
    }

    public void createNewEntity(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
        final String tableClassName = createTableClassName(entityInput.getTableName());
        final String entityClassName = createEntityClassName(entityInput.getEntityName());

        final String tableCode = tableCodeGenerator.generate(tableClassName, entityInput);
        final String entityCode = entityCodeGenerator.generate(entityClassName, tableClassName, entityInput);

        classCreator.generateClass(directory, tableClassName, tableCode);
        classCreator.generateClass(directory, entityClassName, entityCode);
    }

    public List<String> getAlreadyExistingClasses(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
        return Lists.newArrayList();
    }

    @VisibleForTesting
    String createTableClassName(String tableName) {
        return String.format("%sTable", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
    }

    @VisibleForTesting
    String createEntityClassName(String entityName) {
        return String.format("%sEntity", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

}
