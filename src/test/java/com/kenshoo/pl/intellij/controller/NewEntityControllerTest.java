package com.kenshoo.pl.intellij.controller;

import com.kenshoo.pl.intellij.codegen.ClassCreator;
import com.kenshoo.pl.intellij.codegen.EntityCodeGenerator;
import com.kenshoo.pl.intellij.codegen.EntityUniqueKeyCodeGenerator;
import com.kenshoo.pl.intellij.codegen.TableCodeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class NewEntityControllerTest {

    @Mock
    private TableCodeGenerator tableCodeGenerator;

    @Mock
    private EntityCodeGenerator entityCodeGenerator;

    @Mock
    private EntityUniqueKeyCodeGenerator uniqueKeyCodeGenerator;

    @Mock
    private ClassCreator classCreator;

    private final NewEntityController underTest = new NewEntityController(classCreator, tableCodeGenerator, entityCodeGenerator, uniqueKeyCodeGenerator);

    @Test
    public void createTableClassName_convert_tableName_to_className_as_expected() {
        final String className = underTest.createTableClassName("data_base");
        assertThat(className, is("DataBaseTable"));
    }

    @Test
    public void createEntityClassName_convert_entityName_to_className_as_expected() {
        final String className = underTest.createEntityClassName("abc");
        assertThat(className, is("AbcEntity"));
    }
}