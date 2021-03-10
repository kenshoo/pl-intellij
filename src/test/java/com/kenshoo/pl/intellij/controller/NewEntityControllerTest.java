package com.kenshoo.pl.intellij.controller;

import com.kenshoo.pl.intellij.codegen.*;
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
    private EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;

    @Mock
    private CreateCommandCodeGenerator createCommandCodeGenerator;

    @Mock
    private UpdateCommandCodeGenerator updateCommandCodeGenerator;

    @Mock
    private UpsertCommandCodeGenerator upsertCommandCodeGenerator;

    @Mock
    private DeleteCommandCodeGenerator deleteCommandCodeGenerator;

    @Mock
    private SourceCodeFilePersister sourceCodeFilePersister;

    private final NewEntityController underTest =
        NewEntityController.builder()
                           .sourceCodeFilePersister(sourceCodeFilePersister)
                           .tableCodeGenerator(tableCodeGenerator)
                           .entityCodeGenerator(entityCodeGenerator)
                           .entityPersistenceCodeGenerator(entityPersistenceCodeGenerator)
                           .createCommandCodeGenerator(createCommandCodeGenerator)
                           .updateCommandCodeGenerator(updateCommandCodeGenerator)
                           .upsertCommandCodeGenerator(upsertCommandCodeGenerator)
                           .deleteCommandCodeGenerator(deleteCommandCodeGenerator)
                           .build();

    @Test
    public void createTableTypeName_convert_tableName_to_typeName_as_expected() {
        final String typeName = underTest.createTableTypeName("data_base");
        assertThat(typeName, is("DataBaseTable"));
    }

    @Test
    public void createEntityTypeName_convert_entityName_to_typeName_as_expected() {
        final String typeName = underTest.createEntityTypeName("abc");
        assertThat(typeName, is("AbcEntity"));
    }
}