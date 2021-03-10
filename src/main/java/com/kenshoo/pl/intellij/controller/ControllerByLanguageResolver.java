package com.kenshoo.pl.intellij.controller;

import com.kenshoo.pl.intellij.codegen.java.*;
import com.kenshoo.pl.intellij.codegen.scala.*;
import com.kenshoo.pl.intellij.model.Language;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kenshoo.pl.intellij.model.Language.JAVA;
import static com.kenshoo.pl.intellij.model.Language.SCALA;

public class ControllerByLanguageResolver {

    public static final ControllerByLanguageResolver INSTANCE = new ControllerByLanguageResolver();

    private static final NewEntityController JAVA_CONTROLLER =
        NewEntityController.builder()
                           .classCreator(JavaClassCreator.INSTANCE)
                           .tableCodeGenerator(JavaTableCodeGenerator.INSTANCE)
                           .entityCodeGenerator(JavaEntityCodeGenerator.INSTANCE)
                           .entityPersistenceCodeGenerator(JavaEntityPersistenceCodeGenerator.INSTANCE)
                           .createCommandCodeGenerator(JavaCreateCommandCodeGenerator.INSTANCE)
                           .updateCommandCodeGenerator(JavaUpdateCommandCodeGenerator.INSTANCE)
                           .upsertCommandCodeGenerator(JavaUpsertCommandCodeGenerator.INSTANCE)
                           .deleteCommandCodeGenerator(JavaDeleteCommandCodeGenerator.INSTANCE)
                           .build();

    private static final NewEntityController SCALA_CONTROLLER =
        NewEntityController.builder()
                           .classCreator(ScalaClassCreator.INSTANCE)
                           .tableCodeGenerator(ScalaTableCodeGenerator.INSTANCE)
                           .entityCodeGenerator(ScalaEntityCodeGenerator.INSTANCE)
                           .entityPersistenceCodeGenerator(ScalaEntityPersistenceCodeGenerator.INSTANCE)
                           .createCommandCodeGenerator(ScalaCreateCommandCodeGenerator.INSTANCE)
                           .updateCommandCodeGenerator(ScalaUpdateCommandCodeGenerator.INSTANCE)
                           .upsertCommandCodeGenerator(ScalaUpsertCommandCodeGenerator.INSTANCE)
                           .deleteCommandCodeGenerator(ScalaDeleteCommandCodeGenerator.INSTANCE)
                           .build();

    private static final Map<Language, NewEntityController> CONTROLLER_MAP =
        Stream.of(ImmutablePair.of(JAVA, JAVA_CONTROLLER),
                  ImmutablePair.of(SCALA, SCALA_CONTROLLER))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    public NewEntityController resolve(@NotNull final Language language) {
        return Optional.ofNullable(CONTROLLER_MAP.get(language))
                       .orElseThrow(() -> new IllegalStateException(String.format("The language %s is unsupported by the plugin",
                                                                                  language)));
    }
}
