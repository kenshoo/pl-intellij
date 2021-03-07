package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;
import com.kenshoo.pl.intellij.model.FieldFlags;

import java.util.List;
import java.util.stream.Collectors;

public class ScalaEntityCodeGenerator implements EntityCodeGenerator {

    public static final ScalaEntityCodeGenerator INSTANCE = new ScalaEntityCodeGenerator();

    public String generate(String entityClassName, String tableClassName, EntityInput input) {
        // TODO
        return "";
    }
}
