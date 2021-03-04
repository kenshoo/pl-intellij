package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.actions.JavaCreateTemplateInPackageAction;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;

import java.util.Arrays;

class SourcePackageClassifier {

    boolean containsJava(final ProjectFileIndex projectFileIndex, final PsiDirectory[] dirs) {
        return Arrays.stream(dirs)
                     .filter(dir -> projectFileIndex.isUnderSourceRootOfType(dir.getVirtualFile(), JavaModuleSourceRootTypes.SOURCES))
                     .anyMatch(this::isJavaPackage);
    }

    boolean isJavaPackage(final PsiDirectory dir) {
        return JavaCreateTemplateInPackageAction.doCheckPackageExists(dir);
    }
}
