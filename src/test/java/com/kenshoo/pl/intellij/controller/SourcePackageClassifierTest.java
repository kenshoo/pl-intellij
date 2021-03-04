package com.kenshoo.pl.intellij.controller;

import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SourcePackageClassifierTest {

    @Mock
    private ProjectFileIndex projectFileIndex;

    @Mock
    private PsiDirectory dir1;

    @Mock
    private PsiDirectory dir2;

    @Mock
    private VirtualFile virtualFile1;

    @Mock
    private VirtualFile virtualFile2;

    @Test
    public void containsJavaWhenFirstDirMatchesConditionsShouldReturnTrue() {
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier sourcePackageClassifier = new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return true;
            }
        };

        assertThat(sourcePackageClassifier.containsJava(projectFileIndex, new PsiDirectory[]{dir1, dir2}), is(true));
    }

    @Test
    public void containsJavaWhenFirstDirIsNotJavaSourceAndSecondDirMatchesConditionsShouldReturnTrue() {
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier sourcePackageClassifier = new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return true;
            }
        };

        assertThat(sourcePackageClassifier.containsJava(projectFileIndex, new PsiDirectory[]{dir1, dir2}), is(true));
    }

    @Test
    public void containsJavaWhenFirstDirIsJavaSourceButNotPackageAndSecondDirMatchesConditionsShouldReturnTrue() {
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier sourcePackageClassifier = new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return dir == dir2;
            }
        };

        assertThat(sourcePackageClassifier.containsJava(projectFileIndex, new PsiDirectory[]{dir1, dir2}), is(true));
    }

    @Test
    public void containsJavaWhenBothAreJavaSourcesButNotPackagesShouldReturnFalse() {
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(true);

        final SourcePackageClassifier sourcePackageClassifier = new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return false;
            }
        };

        assertThat(sourcePackageClassifier.containsJava(projectFileIndex, new PsiDirectory[]{dir1, dir2}), is(false));
    }

    @Test
    public void containsJavaWhenNeitherAreJavaSourcesShouldReturnFalse() {
        when(dir1.getVirtualFile()).thenReturn(virtualFile1);
        when(dir2.getVirtualFile()).thenReturn(virtualFile2);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile1, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);
        when(projectFileIndex.isUnderSourceRootOfType(virtualFile2, JavaModuleSourceRootTypes.SOURCES)).thenReturn(false);

        final SourcePackageClassifier sourcePackageClassifier = new SourcePackageClassifier() {
            boolean isJavaPackage(final PsiDirectory dir) {
                return true;
            }
        };

        assertThat(sourcePackageClassifier.containsJava(projectFileIndex, new PsiDirectory[]{dir1, dir2}), is(false));
    }
}