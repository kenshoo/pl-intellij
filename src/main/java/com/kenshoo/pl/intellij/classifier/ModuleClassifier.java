package com.kenshoo.pl.intellij.classifier;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.util.CommonProcessors.FindProcessor;
import com.kenshoo.pl.intellij.model.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.kenshoo.pl.intellij.model.Language.JAVA;
import static com.kenshoo.pl.intellij.model.Language.SCALA;

public class ModuleClassifier {

    public Language determineLanguage(@NotNull final DataContext dataContext) {
        final FindProcessor<? super Library> scalaFindProcessor = newScalaFindProcessor();

        Optional.ofNullable(LangDataKeys.MODULE.getData(dataContext))
                .map(OrderEnumerator::orderEntries)
                .map(OrderEnumerator::librariesOnly)
                .ifPresent(orderEnumerator -> orderEnumerator.forEachLibrary(scalaFindProcessor));

        return Optional.ofNullable(scalaFindProcessor.getFoundValue())
                       .map(__ -> SCALA)
                       .orElse(JAVA);
    }

    // Visible for testing
    FindProcessor<? super Library> newScalaFindProcessor() {
        return new ScalaLibraryFindProcessor();
    }
}


