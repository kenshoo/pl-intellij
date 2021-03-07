package com.kenshoo.pl.intellij.controller;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlEntityWizardActionTest {

    @Mock
    private AnActionEvent event;

    @Mock
    private DataContext dataContext;

    @Mock
    private SourcePackageClassifier sourcePackageClassifier;

    @InjectMocks
    private PlEntityWizardAction action;

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
}