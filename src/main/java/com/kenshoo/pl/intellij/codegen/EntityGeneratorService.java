package com.kenshoo.pl.intellij.codegen;

import com.google.common.collect.ImmutableList;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.kenshoo.pl.intellij.model.EntityInput;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGeneratorService {

    public static EntityGeneratorService INSTANCE = new EntityGeneratorService();

    private List<CodeGenerator> codeGenerators = ImmutableList.of(TableCodeGenerator.INSTANCE);

    public void generateEntity(PsiJavaDirectoryImpl directory, EntityInput entityInput) {
        codeGenerators.forEach(codeGenerator -> {
            final Template template = codeGenerator.generate(directory, entityInput);
            final String className = template.getClassName();
            try {
                JavaDirectoryService.getInstance().checkCreateClass(directory, className);
                JavaDirectoryService.getInstance().createClass(directory, className, template.getTemplateName(), false, template.getParameters());
            }
            catch (IncorrectOperationException e){
                System.out.println("ERROR: class name already exists in this package.");
            }
        });


    }
}
