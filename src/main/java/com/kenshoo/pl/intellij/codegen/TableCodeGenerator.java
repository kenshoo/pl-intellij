package com.kenshoo.pl.intellij.codegen;

import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.model.EntityInput;

import java.util.HashMap;
import java.util.Map;

public class TableCodeGenerator implements CodeGenerator {

    public static final TableCodeGenerator INSTANCE = new TableCodeGenerator();

    public static final String TEMPLATE_MANE = "Persistence Layers Table Generator";
    public static final String TEMPLATE_CONTENT = "package ${PACKAGE_NAME};#end\n" +
            "${IMPORTS}\n" +
            "public class ${CLASS_DECLARATION}{\n" +
            "   ${CONTENT}\n" +
            "}";

    @Override
    public Template generate(PsiJavaDirectoryImpl directory, EntityInput input) {
        final String className = input.getTableName();
        final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(directory);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("PACKAGE_NAME", aPackage.getName());
        parameters.put("IMPORTS", "");
        parameters.put("CLASS_DECLARATION", className);
        parameters.put("CONTENT", "private int d;");

        TemplateGenerator.INSTANCE.generate(directory, TEMPLATE_MANE, TEMPLATE_CONTENT);
        return new Template(className, TEMPLATE_MANE, parameters);
    }
}
