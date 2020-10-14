package com.kenshoo.pl.intellij.controller;

import com.google.common.base.CaseFormat;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TemplateGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;

import java.util.List;

public class NewEntityController {

    public final static NewEntityController INSTANCE = new NewEntityController();

    private final TableCodeGenerator tableCodeGenerator = TableCodeGenerator.INSTANCE;
    private final TemplateGenerator templateGenerator = TemplateGenerator.INSTANCE;

    public void createNewEntity(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
        final String tableClassName = createTableClassName(entityInput.getTableName());

        final String tableCode = tableCodeGenerator.generate(tableClassName, entityInput, directory);

        templateGenerator.generateClass(directory, tableClassName, tableCode);
    }

    public List<String> getAlreadyExistingClasses(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
       return null;
    }

    private String createTableClassName(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }

}
