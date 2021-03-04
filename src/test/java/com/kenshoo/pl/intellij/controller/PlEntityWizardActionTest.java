package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlEntityWizardActionTest {

    @Mock
    private AnActionEvent event;

    @Mock
    private DataContext dataContext;

    @Mock
    private Project project;

    @Mock
    private ProjectRootManager projectRootManager;

    @Mock
    private ProjectFileIndex projectFileIndex;

    @Mock
    private SourcePackageClassifier sourcePackageClassifier;

    @Mock
    private IdeView ideView;

    @Mock
    private PsiDirectory dir1;

    @Mock
    private PsiDirectory dir2;

    private final PsiDirectory[] dirs = new PsiDirectory[]{dir1, dir2};

    @InjectMocks
    private PlEntityWizardAction action;

    @Test
    public void updateWhenProjectAndViewDirsExistAndDirsIncludeJavaPackageShouldBeEnabledAndVisible() {
        final Presentation presentation = new Presentation();

        when(event.getDataContext()).thenReturn(dataContext);
        when(CommonDataKeys.PROJECT.getData(dataContext)).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(LangDataKeys.IDE_VIEW.getData(dataContext)).thenReturn(ideView);
        when(ideView.getDirectories()).thenReturn(dirs);
        when(sourcePackageClassifier.containsJava(eq(projectFileIndex), aryEq(dirs))).thenReturn(true);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabledAndVisible(), is(true));
    }

    @Test
    public void updateWhenProjectAndViewDirsExistAndDirsAreEmptyShouldBeDisabledAndHidden() {
        final Presentation presentation = new Presentation();
        presentation.setEnabledAndVisible(true);

        when(event.getDataContext()).thenReturn(dataContext);
        when(CommonDataKeys.PROJECT.getData(dataContext)).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(LangDataKeys.IDE_VIEW.getData(dataContext)).thenReturn(ideView);
        when(ideView.getDirectories()).thenReturn(new PsiDirectory[]{});
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabled(), is(false));
        assertThat(presentation.isVisible(), is(false));
    }

    @Test
    public void updateWhenProjectAndViewDirsExistAndDirsDoNotIncludeJavaPackageShouldBeDisabledAndHidden() {
        final Presentation presentation = new Presentation();
        presentation.setEnabledAndVisible(true);

        when(event.getDataContext()).thenReturn(dataContext);
        when(CommonDataKeys.PROJECT.getData(dataContext)).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(LangDataKeys.IDE_VIEW.getData(dataContext)).thenReturn(ideView);
        when(ideView.getDirectories()).thenReturn(dirs);
        when(sourcePackageClassifier.containsJava(eq(projectFileIndex), aryEq(dirs))).thenReturn(false);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabled(), is(false));
        assertThat(presentation.isVisible(), is(false));
    }

    @Test
    public void updateWhenProjectExistsButViewDoesntShouldBeDisabledAndEmpty() {
        final Presentation presentation = new Presentation();
        presentation.setEnabledAndVisible(true);

        when(event.getDataContext()).thenReturn(dataContext);
        when(CommonDataKeys.PROJECT.getData(dataContext)).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(LangDataKeys.IDE_VIEW.getData(dataContext)).thenReturn(null);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabled(), is(false));
        assertThat(presentation.isVisible(), is(false));
    }

    @Test
    public void updateWhenProjectDoesntExistShouldBeDisabledAndEmpty() {
        final Presentation presentation = new Presentation();
        presentation.setEnabledAndVisible(true);

        when(event.getDataContext()).thenReturn(dataContext);
        when(CommonDataKeys.PROJECT.getData(dataContext)).thenReturn(null);
        when(event.getPresentation()).thenReturn(presentation);

        action.update(event);

        assertThat(presentation.isEnabled(), is(false));
        assertThat(presentation.isVisible(), is(false));
    }
}