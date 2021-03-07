package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntitySchemaField;
import com.kenshoo.pl.intellij.model.FieldFlags;

import java.util.List;
import java.util.stream.Collectors;

public class ScalaTableCodeGenerator implements TableCodeGenerator {

    public static final ScalaTableCodeGenerator INSTANCE = new ScalaTableCodeGenerator();

    public String generate(String className, EntityInput input) {
        // TODO
        return "";
    }
}
