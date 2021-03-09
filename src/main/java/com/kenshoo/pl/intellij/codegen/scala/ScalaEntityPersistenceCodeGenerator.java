package com.kenshoo.pl.intellij.codegen.scala;

import com.kenshoo.pl.intellij.codegen.EntityPersistenceCodeGenerator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScalaEntityPersistenceCodeGenerator implements EntityPersistenceCodeGenerator {

    public static final ScalaEntityPersistenceCodeGenerator INSTANCE = new ScalaEntityPersistenceCodeGenerator();

    public String generate(String entityTypeName, String entityPersistenceTypeName) {
        return Stream.<String>builder()
            .add("import com.kenshoo.pl.entity._")
            .add("import com.kenshoo.pl.entity.spi.helpers.EntityChangeCompositeValidator")
            .add("import scala.collection.JavaConverters._")
            .add("")
            .add("class " + entityPersistenceTypeName + "(val plContext: PLContext) {")
            .add("")

            .add("private val pl = new PersistenceLayer[" + entityTypeName + "](plContext)")
            .add("")

            // --- flow config builder -- //

            .add("def flowBuilder: ChangeFlowConfig.Builder[" +  entityTypeName + "] = {")
            .add("val simpleValidators = new EntityChangeCompositeValidator[" + entityTypeName + "]()")
            .add("// register simple validators to this val.")
            .add("")
            .add("ChangeFlowConfigBuilderFactory")
            .add("  .newInstance(plContext, " +  entityTypeName + ".INSTANCE)")
            .add("  .withValidator(simpleValidators)")
            .add("}")
            .add("")

            // --- method: create -- //

            .add("def create(commands: Seq[_ <: CreateEntityCommand[" + entityTypeName + "]])")
            .add(": CreateResult[" + entityTypeName + ", Identifier[" + entityTypeName + "]] =")
            .add("pl.create(commands.asJava, flowBuilder.build())")
            .add("")

            // --- method: update -- //

            .add("def update[ID <: Identifier[" + entityTypeName + "]](commands: Seq[_ <: UpdateEntityCommand[" + entityTypeName + ", ID]])")
            .add(": UpdateResult[" + entityTypeName + ", ID] =")
            .add("pl.update(commands.asJava, flowBuilder.build())")
            .add("")

            // --- method: upsert -- //

            .add("def upsert[ID <: Identifier[" + entityTypeName + "]](commands: Seq[_ <: InsertOnDuplicateUpdateCommand[" + entityTypeName + ", ID]])")
            .add(": InsertOnDuplicateUpdateResult[" + entityTypeName + ", ID] =")
            .add("pl.upsert(commands.asJava, flowBuilder.build())")
            .add("")

            // --- method: delete -- //

            .add("def delete[ID <: Identifier[" + entityTypeName + "]](commands: Seq[_ <: DeleteEntityCommand[" + entityTypeName + ", ID]])")
            .add(": DeleteResult[" + entityTypeName + ", ID] =")
            .add("pl.delete(commands.asJava, flowBuilder.build())")
            .add("")

            .add("}")

            .build()
            .collect(Collectors.joining("\n"));
    }
}
