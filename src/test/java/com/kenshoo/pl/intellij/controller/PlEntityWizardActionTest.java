package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.classifier.ModuleClassifier;
import com.kenshoo.pl.intellij.classifier.SourcePackageClassifier;
import com.kenshoo.pl.intellij.model.Language;
import com.kenshoo.pl.intellij.view.NewEntityFormWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.kenshoo.pl.intellij.model.Language.SCALA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlEntityWizardActionTest {

    @Mock
    private AnActionEvent event;

    @Mock
    private DataContext dataContext;

    @Mock
    private SourcePackageClassifier sourcePackageClassifier;

    @Mock
    private ModuleClassifier moduleClassifier;

    @Mock
    private IdeView ideView;

    @Mock
    private PsiDirectory dir;

    @Mock
    private NewEntityFormWrapper newEntityFormWrapper;

    @InjectMocks
    private StubPlEntityWizardAction action;

    @Test
    public void updateWhenIsJavaSourcePackageShouldBeEnabledAndVisible() {
        final Presentation presentation = new Presentation();

        when(event.getDataContext()).thenReturn(dataContext);
        when(sourcePackageClassifier.isJava(dataContext)).thenReturn(true);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabledAndVisible(), is(true));
    }

    @Test
    public void updateWhenNotJavaSourcePackageShouldBeDisabledAndInvisible() {
        final Presentation presentation = new Presentation();
        presentation.setEnabledAndVisible(true);

        when(event.getDataContext()).thenReturn(dataContext);
        when(sourcePackageClassifier.isJava(dataContext)).thenReturn(false);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat("Should be disabled: ", presentation.isEnabled(), is(false));
        assertThat("Should be invisible: ", presentation.isVisible(), is(false));
    }

    @Test
    public void actionPerformedWhenIdeViewExistsShouldShowEntityForm() {
        when(event.getDataContext()).thenReturn(dataContext);
        when(dataContext.getData(LangDataKeys.IDE_VIEW.getName())).thenReturn(ideView);
        when(ideView.getOrChooseDirectory()).thenReturn(dir);
        when(moduleClassifier.determineLanguage(dataContext)).thenReturn(SCALA);

        action.actionPerformed(event);

        verify(newEntityFormWrapper).show();

        assertThat("Incorrect dir: ", action.dir, is(dir));
        assertThat("Incorrect language: ", action.language, is(SCALA));
    }

    @Test
    public void actionPerformedWhenIdeViewDoesNotExistShouldDoNothing() {
        when(event.getDataContext()).thenReturn(dataContext);
        when(dataContext.getData(LangDataKeys.IDE_VIEW.getName())).thenReturn(null);

        action.actionPerformed(event);

        verifyNoMoreInteractions(newEntityFormWrapper);
    }

    private static class StubPlEntityWizardAction extends PlEntityWizardAction {

        private final NewEntityFormWrapper newEntityFormWrapper;

        private PsiDirectory dir;
        private Language language;

        private StubPlEntityWizardAction(final SourcePackageClassifier sourcePackageClassifier,
                                         final ModuleClassifier moduleClassifier,
                                         final NewEntityFormWrapper newEntityFormWrapper) {
            super(sourcePackageClassifier, moduleClassifier);
            this.newEntityFormWrapper = newEntityFormWrapper;
        }

        @Override
        NewEntityFormWrapper newEntityFormWrapper(final PsiDirectory dir, final Language language) {
            this.dir = dir;
            this.language = language;
            return newEntityFormWrapper;
        }
    }
}