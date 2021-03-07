package com.kenshoo.pl.intellij.controller;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.codegen.*;
import com.kenshoo.pl.intellij.model.EntityInput;


public class NewEntityController {

    private final ClassCreator classCreator;
    private final TableCodeGenerator tableCodeGenerator;
    private final EntityCodeGenerator entityCodeGenerator;
    private final EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;
    private final CreateCommandCodeGenerator createCommandCodeGenerator;
    private final UpdateCommandCodeGenerator updateCommandCodeGenerator;
    private final UpsertCommandCodeGenerator upsertCommandCodeGenerator;
    private final DeleteCommandCodeGenerator deleteCommandCodeGenerator;

    private NewEntityController(
            ClassCreator classCreator,
            TableCodeGenerator tableCodeGenerator,
            EntityCodeGenerator entityCodeGenerator,
            EntityPersistenceCodeGenerator entityPersistenceCodeGenerator,
            CreateCommandCodeGenerator createCommandCodeGenerator,
            UpdateCommandCodeGenerator updateCommandCodeGenerator,
            UpsertCommandCodeGenerator upsertCommandCodeGenerator,
            DeleteCommandCodeGenerator deleteCommandCodeGenerator
            ) {
        this.classCreator = classCreator;
        this.tableCodeGenerator = tableCodeGenerator;
        this.entityCodeGenerator = entityCodeGenerator;
        this.entityPersistenceCodeGenerator = entityPersistenceCodeGenerator;
        this.createCommandCodeGenerator = createCommandCodeGenerator;
        this.updateCommandCodeGenerator = updateCommandCodeGenerator;
        this.upsertCommandCodeGenerator = upsertCommandCodeGenerator;
        this.deleteCommandCodeGenerator = deleteCommandCodeGenerator;
    }

    public void createNewEntity(PsiDirectory directory, EntityInput input) {
        final String tableClassName = createTableClassName(input.getTableName());
        final String entityClassName = createEntityClassName(input.getEntityName());
        final String entityPersistenceClassName = createEntityPersistenceClassName(input.getEntityName());
        final String createCommandClassName = "Create" + input.getEntityName();
        final String updateCommandClassName = "Update" + input.getEntityName();
        final String upsertCommandClassName = "Upsert" + input.getEntityName();
        final String deleteCommandClassName = "Delete" + input.getEntityName();

        final String tableCode = tableCodeGenerator.generate(tableClassName, input);
        final String entityCode = entityCodeGenerator.generate(entityClassName, tableClassName, input);
        final String entityPersistenceCode = entityPersistenceCodeGenerator.generate(entityClassName, entityPersistenceClassName);
        final String createCommandCode = createCommandCodeGenerator.generate(createCommandClassName, entityClassName);
        final String updateCommandCode = updateCommandCodeGenerator.generate(updateCommandClassName, entityClassName);
        final String upsertCommandCode = upsertCommandCodeGenerator.generate(upsertCommandClassName, entityClassName);
        final String deleteCommandCode = deleteCommandCodeGenerator.generate(deleteCommandClassName, entityClassName);

        classCreator.generateClass(directory, tableClassName, tableCode);
        classCreator.generateClass(directory, entityClassName, entityCode);
        classCreator.generateClass(directory, entityPersistenceClassName, entityPersistenceCode);
        classCreator.generateClass(directory, createCommandClassName, createCommandCode);
        classCreator.generateClass(directory, updateCommandClassName, updateCommandCode);
        classCreator.generateClass(directory, upsertCommandClassName, upsertCommandCode);
        classCreator.generateClass(directory, deleteCommandClassName, deleteCommandCode);
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
    String createEntityPersistenceClassName(String entityName) {
        return String.format("%sPersistence", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityName));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ClassCreator classCreator;
        private TableCodeGenerator tableCodeGenerator;
        private EntityCodeGenerator entityCodeGenerator;
        private EntityPersistenceCodeGenerator entityPersistenceCodeGenerator;
        private CreateCommandCodeGenerator createCommandCodeGenerator;
        private UpdateCommandCodeGenerator updateCommandCodeGenerator;
        private UpsertCommandCodeGenerator upsertCommandCodeGenerator;
        private DeleteCommandCodeGenerator deleteCommandCodeGenerator;

        private Builder() {
            // singleton
        }

        public Builder classCreator(ClassCreator classCreator) {
            this.classCreator = classCreator;
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

        public Builder createCommandCodeGenerator(CreateCommandCodeGenerator createCommandCodeGenerator) {
            this.createCommandCodeGenerator = createCommandCodeGenerator;
            return this;
        }

        public Builder updateCommandCodeGenerator(UpdateCommandCodeGenerator updateCommandCodeGenerator) {
            this.updateCommandCodeGenerator = updateCommandCodeGenerator;
            return this;
        }

        public Builder upsertCommandCodeGenerator(UpsertCommandCodeGenerator upsertCommandCodeGenerator) {
            this.upsertCommandCodeGenerator = upsertCommandCodeGenerator;
            return this;
        }

        public Builder deleteCommandCodeGenerator(DeleteCommandCodeGenerator deleteCommandCodeGenerator) {
            this.deleteCommandCodeGenerator = deleteCommandCodeGenerator;
            return this;
        }

        public NewEntityController build() {
            return new NewEntityController(classCreator,
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
