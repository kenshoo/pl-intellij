package com.kenshoo.pl.intellij.classifier;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.util.CommonProcessors.FindProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.kenshoo.pl.intellij.model.Language.JAVA;
import static com.kenshoo.pl.intellij.model.Language.SCALA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModuleClassifierTest {

    @Mock
    private DataContext dataContext;

    @Mock
    private Module module;

    @Mock
    private ModuleRootManager moduleRootManager;

    @Mock
    private OrderEnumerator orderEnumerator;

    @Mock
    private FindProcessor<? super Library> scalaLibraryFindProcessor;

    @Mock
    private Library scalaLibrary;

    private ModuleClassifier moduleClassifier;

    @Before
    public void setUp() {
        moduleClassifier = new ModuleClassifier() {

            @Override
            FindProcessor<? super Library> newScalaFindProcessor() {
                return scalaLibraryFindProcessor;
            }
        };
    }

    @Test
    public void determineLanguageWhenContainsScalaLibraryShouldReturnJava() {
        when(dataContext.getData(LangDataKeys.MODULE.getName())).thenReturn(module);
        when(module.getComponent(ModuleRootManager.class)).thenReturn(moduleRootManager);
        when(moduleRootManager.orderEntries()).thenReturn(orderEnumerator);
        when(orderEnumerator.librariesOnly()).thenReturn(orderEnumerator);
        when(scalaLibraryFindProcessor.getFoundValue()).thenReturn(scalaLibrary);

        assertThat(moduleClassifier.determineLanguage(dataContext), is(SCALA));

        verify(orderEnumerator).forEachLibrary(scalaLibraryFindProcessor);
    }

    @Test
    public void determineLanguageWhenDoesntContainScalaLibraryShouldReturnJava() {
        when(dataContext.getData(LangDataKeys.MODULE.getName())).thenReturn(module);
        when(module.getComponent(ModuleRootManager.class)).thenReturn(moduleRootManager);
        when(moduleRootManager.orderEntries()).thenReturn(orderEnumerator);
        when(orderEnumerator.librariesOnly()).thenReturn(orderEnumerator);
        when(scalaLibraryFindProcessor.getFoundValue()).thenReturn(null);

        assertThat(moduleClassifier.determineLanguage(dataContext), is(JAVA));

        verify(orderEnumerator).forEachLibrary(scalaLibraryFindProcessor);
    }

    @Test
    public void determineLanguageWhenModuleDoesntExistShouldReturnJava() {
        when(dataContext.getData(LangDataKeys.MODULE.getName())).thenReturn(null);

        assertThat(moduleClassifier.determineLanguage(dataContext), is(JAVA));
    }
}