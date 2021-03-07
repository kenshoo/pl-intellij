package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SourcePackageClassifierTest {

    @Mock
    private DataContext dataContext;

    @Mock
    private Project project;

    @Mock
    private ProjectRootManager projectRootManager;

    @Mock
    private ProjectFileIndex projectFileIndex;

    @Mock
    private IdeView ideView;

    @Mock
    private PsiDirectory dir1;

    @Mock
    private PsiDirectory dir2;

    @Mock
    private VirtualFile virtualFile1;

    @Mock
    private VirtualFile virtualFile2;

    private PsiDirectory[] dirs;

    @Before
    public void setUp() {
        dirs = new PsiDirectory[]{dir1, dir2};
    }

    @Test
    public void isJavaWhenFirstDirMatchesConditionsShouldReturnTrue() {
        mockExtractDirs();
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(true));
    }

    @Test
    public void isJavaWhenFirstDirIsNotJavaSourceAndSecondDirMatchesConditionsShouldReturnTrue() {
        mockExtractDirs();
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(true));
    }

    @Test
    public void isJavaWhenFirstDirIsJavaSourceButNotPackageAndSecondDirMatchesConditionsShouldReturnTrue() {
        mockExtractDirs();
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier classifier = createClassifier(dir2::equals);

        assertThat(classifier.isJava(dataContext), is(true));
    }

    @Test
    public void isJavaWhenBothDirsAreJavaSourcesButNotPackagesShouldReturnFalse() {
        mockExtractDirs();
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier classifier = createClassifier(__ -> false);

        assertThat(classifier.isJava(dataContext), is(false));
    }

    @Test
    public void isJavaWhenNeitherDirIsJavaSourceShouldReturnFalse() {
        mockExtractDirs();
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(false));
    }

    @Test
    public void isJavaWhenViewExistButDirsAreEmptyShouldReturnFalse() {
        when(dataContext.getData(CommonDataKeys.PROJECT.getName())).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(dataContext.getData(LangDataKeys.IDE_VIEW.getName())).thenReturn(ideView);
        when(ideView.getDirectories()).thenReturn(new PsiDirectory[]{});

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(false));
    }

    @Test
    public void isJavaWhenProjectExistsButViewDoesntShouldReturnFalse() {
        when(dataContext.getData(CommonDataKeys.PROJECT.getName())).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(dataContext.getData(LangDataKeys.IDE_VIEW.getName())).thenReturn(null);

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(false));
    }

    @Test
    public void isJavaWhenProjectDoesntExistShouldReturnFalse() {
        when(dataContext.getData(CommonDataKeys.PROJECT.getName())).thenReturn(null);

        final SourcePackageClassifier classifier = createClassifier(__ -> true);

        assertThat(classifier.isJava(dataContext), is(false));
    }

    private void mockExtractDirs() {
        when(dataContext.getData(CommonDataKeys.PROJECT.getName())).thenReturn(project);
        when(project.getComponent(ProjectRootManager.class)).thenReturn(projectRootManager);
        when(projectRootManager.getFileIndex()).thenReturn(projectFileIndex);
        when(dataContext.getData(LangDataKeys.IDE_VIEW.getName())).thenReturn(ideView);
        when(ideView.getDirectories()).thenReturn(dirs);
    }

    private SourcePackageClassifier createClassifier(final Predicate<PsiDirectory> javaPackagePredicate) {
        return new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return javaPackagePredicate.test(dir);
            }
        };
    }
}