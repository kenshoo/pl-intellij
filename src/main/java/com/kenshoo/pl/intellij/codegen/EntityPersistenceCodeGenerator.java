package com.kenshoo.pl.intellij.codegen;

public class EntityPersistenceCodeGenerator {

    public static final EntityPersistenceCodeGenerator INSTANCE = new EntityPersistenceCodeGenerator();

    private static final String NEW_LINE = "\n";

    public String generate(String entityClassName, String entityPersistenceClassName) {
        return new StringBuilder()
                .append("import com.kenshoo.pl.entity.*;")
                .append(NEW_LINE)
                .append("import java.util.Collection;")
                .append(NEW_LINE)
                .append("import java.util.List;")
                .append(NEW_LINE)
                .append("import java.util.stream.Stream;")
                .append(NEW_LINE)
                .append("public class ").append(entityPersistenceClassName).append(" {")
                .append(NEW_LINE)

                // --- constructor -- //

                .append(NEW_LINE)
                .append("private final PersistenceLayer<").append(entityClassName).append("> pl;")
                .append(NEW_LINE)
                .append("private final PLContext plContext;")
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("public ").append(entityPersistenceClassName).append("(PLContext plContext) {")
                .append(NEW_LINE)
                .append("this.pl = new PersistenceLayer<>(plContext);")
                .append(NEW_LINE)
                .append("this.plContext = plContext;")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                // --- flow config builder -- //

                .append(NEW_LINE)
                .append("private ChangeFlowConfig.Builder<").append(entityClassName).append("> flowBuilder() {")
                .append(NEW_LINE)
                .append("return ChangeFlowConfigBuilderFactory.newInstance(plContext, ").append(entityClassName).append(".INSTANCE);")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                // --- method: create -- //

                .append(NEW_LINE)
                .append("public CreateResult<").append(entityClassName).append(", Identifier<").append(entityClassName).append(">> create(Collection<CreateEntityCommand<").append(entityClassName).append(">> commands) {")
                .append(NEW_LINE)
                .append("return pl.create(commands, flowBuilder().build());")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                // --- method: update -- //

                .append(NEW_LINE)
                .append("public <ID extends Identifier<").append(entityClassName).append(">>")
                .append(NEW_LINE)
                .append("UpdateResult<").append(entityClassName).append(", ID> update(Collection<UpdateEntityCommand<").append(entityClassName).append(", ID>> commands) {")
                .append(NEW_LINE)
                .append("return pl.update(commands, flowBuilder().build());")
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE)

                .append(NEW_LINE)
                .append("}")
                .toString();
    }


}
