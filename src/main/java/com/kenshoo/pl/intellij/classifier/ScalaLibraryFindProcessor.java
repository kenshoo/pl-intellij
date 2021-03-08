package com.kenshoo.pl.intellij.classifier;

import com.intellij.openapi.roots.impl.libraries.LibraryEx;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.util.CommonProcessors.FindProcessor;
import org.jetbrains.plugins.scala.project.ScalaLibraryType$;

class ScalaLibraryFindProcessor extends FindProcessor<Library> {

    @Override
    protected boolean accept(final Library library) {
        return (library instanceof LibraryEx) &&
            ScalaLibraryType$.MODULE$.apply().getKind().equals(((LibraryEx)library).getKind());
    }
}
