package com.kenshoo.pl.intellij.controller;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.codegen.*;
import com.kenshoo.pl.intellij.model.EntityInput;


public class NewEntityController {

    private final SourceCodeFilePersister sourceCodeFilePersister;
    private final TableCodeGenerator tableCodeGenerator;
    private final EntityCodeGenerator entityCodeGenerator;
    private final EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;
    private final CommandCodeGenerator createCommandCodeGenerator;
    private final CommandCodeGenerator updateCommandCodeGenerator;
    private final CommandCodeGenerator upsertCommandCodeGenerator;
    private final CommandCodeGenerator deleteCommandCodeGenerator;

    private NewEntityController(
            SourceCodeFilePersister sourceCodeFilePersister,
            TableCodeGenerator tableCodeGenerator,
            EntityCodeGenerator entityCodeGenerator,
            EntityPersistenceCodeGenerator entityPersistenceCodeGenerator,
            CommandCodeGenerator createCommandCodeGenerator,
            CommandCodeGenerator updateCommandCodeGenerator,
            CommandCodeGenerator upsertCommandCodeGenerator,
            CommandCodeGenerator deleteCommandCodeGenerator
            ) {
        this.sourceCodeFilePersister = sourceCodeFilePersister;
        this.tableCodeGenerator = tableCodeGenerator;
        this.entityCodeGenerator = entityCodeGenerator;
        this.entityPersistenceCodeGenerator = entityPersistenceCodeGenerator;
        this.createCommandCodeGenerator = createCommandCodeGenerator;
        this.updateCommandCodeGenerator = updateCommandCodeGenerator;
        this.upsertCommandCodeGenerator = upsertCommandCodeGenerator;
        this.deleteCommandCodeGenerator = deleteCommandCodeGenerator;
    }

    public void createNewEntity(PsiDirectory directory, EntityInput input) {
        final String tableTypeName = createTableTypeName(input.getTableName());
        final String entityTypeName = createEntityTypeName(input.getEntityName());
        final String entityPersistenceTypeName = createEntityPersistenceTypeName(input.getEntityName());
        final String createCommandTypeName = "Create" + input.getEntityName();
        final String updateCommandTypeName = "Update" + input.getEntityName();
        final String upsertCommandTypeName = "Upsert" + input.getEntityName();
        final String deleteCommandTypeName = "Delete" + input.getEntityName();

        final String tableCode = tableCodeGenerator.generate(tableTypeName, input);
        final String entityCode = entityCodeGenerator.generate(entityTypeName, tableTypeName, input);
        final String entityPersistenceCode = entityPersistenceCodeGenerator.generate(entityTypeName, entityPersistenceTypeName);
        final String createCommandCode = createCommandCodeGenerator.generate(createCommandTypeName, entityTypeName);
        final String updateCommandCode = updateCommandCodeGenerator.generate(updateCommandTypeName, entityTypeName);
        final String upsertCommandCode = upsertCommandCodeGenerator.generate(upsertCommandTypeName, entityTypeName);
        final String deleteCommandCode = deleteCommandCodeGenerator.generate(deleteCommandTypeName, entityTypeName);

        sourceCodeFilePersister.persist(directory, tableTypeName, tableCode);
        sourceCodeFilePersister.persist(directory, entityTypeName, entityCode);
        sourceCodeFilePersister.persist(directory, entityPersistenceTypeName, entityPersistenceCode);
        sourceCodeFilePersister.persist(directory, createCommandTypeName, createCommandCode);
        sourceCodeFilePersister.persist(directory, updateCommandTypeName, updateCommandCode);
        sourceCodeFilePersister.persist(directory, upsertCommandTypeName, upsertCommandCode);
        sourceCodeFilePersister.persist(directory, deleteCommandTypeName, deleteCommandCode);
    }

    @VisibleForTesting
    String createTableTypeName(String tableName) {
        return String.format("%sTable", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
    }

    @VisibleForTesting
    String createEntityTypeName(String entityName) {
        return String.format("%sEntity", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

    @VisibleForTesting
    String createEntityPersistenceTypeName(String entityName) {
        return String.format("%sPersistence", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SourceCodeFilePersister sourceCodeFilePersister;
        private TableCodeGenerator tableCodeGenerator;
        private EntityCodeGenerator entityCodeGenerator;
        private EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;
        private CommandCodeGenerator createCommandCodeGenerator;
        private CommandCodeGenerator updateCommandCodeGenerator;
        private CommandCodeGenerator upsertCommandCodeGenerator;
        private CommandCodeGenerator deleteCommandCodeGenerator;

        private Builder() {
            // singleton
        }

        public Builder sourceCodeFilePersister(SourceCodeFilePersister sourceCodeFilePersister) {
            this.sourceCodeFilePersister = sourceCodeFilePersister;
            return this;
        }

        public Builder tableCodeGenerator(TableCodeGenerator tableCodeGenerator) {
            this.tableCodeGenerator = tableCodeGenerator;
            return this;
        }

        public Builder entityCodeGenerator(EntityCodeGenerator entityCodeGenerator) {
            this.entityCodeGenerator = entityCodeGenerator;
            return this;
        }

        public Builder entityPersistenceCodeGenerator(EntityPersistenceCodeGenerator entityPersistenceCodeGenerator) {
            this.entityPersistenceCodeGenerator = entityPersistenceCodeGenerator;
            return this;
        }

        public Builder createCommandCodeGenerator(CommandCodeGenerator createCommandCodeGenerator) {
            this.createCommandCodeGenerator = createCommandCodeGenerator;
            return this;
        }

        public Builder updateCommandCodeGenerator(CommandCodeGenerator updateCommandCodeGenerator) {
            this.updateCommandCodeGenerator = updateCommandCodeGenerator;
            return this;
        }

        public Builder upsertCommandCodeGenerator(CommandCodeGenerator upsertCommandCodeGenerator) {
            this.upsertCommandCodeGenerator = upsertCommandCodeGenerator;
            return this;
        }

        public Builder deleteCommandCodeGenerator(CommandCodeGenerator deleteCommandCodeGenerator) {
            this.deleteCommandCodeGenerator = deleteCommandCodeGenerator;
            return this;
        }

        public NewEntityController build() {
            return new NewEntityController(sourceCodeFilePersister,
                                           tableCodeGenerator,
                                           entityCodeGenerator,
                                           entityPersistenceCodeGenerator,
                                           createCommandCodeGenerator,
                                           updateCommandCodeGenerator,
                                           upsertCommandCodeGenerator,
                                           deleteCommandCodeGenerator);
        }
    }
}
