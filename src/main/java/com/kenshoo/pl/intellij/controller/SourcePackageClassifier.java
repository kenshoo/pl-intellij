package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiDirectory;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;

import java.util.Arrays;
import java.util.Optional;

class SourcePackageClassifier {

    boolean isJava(final DataContext dataContext) {
        final Optional<ProjectFileIndex> optionalProjectFileIndex = extractProjectFileIndex(dataContext);
        final Optional<PsiDirectory[]> optionalDirs = extractDirs(dataContext);

        return optionalProjectFileIndex
            .flatMap(projectFileIndex -> optionalDirs.filter(dirs -> containsJava(projectFileIndex, dirs)))
            .isPresent();
    }

    private Optional<PsiDirectory[]> extractDirs(final DataContext dataContext) {
        return Optional.ofNullable(LangDataKeys.IDE_VIEW.getData(dataContext))
                       .map(IdeView::getDirectories)
                       .filter(dirs -> !ArrayUtils.isEmpty(dirs));
    }

    private Optional<ProjectFileIndex> extractProjectFileIndex(final DataContext dataContext) {
        return Optional.ofNullable(CommonDataKeys.PROJECT.getData(dataContext))
                       .map(ProjectRootManager::getInstance)
                       .map(ProjectRootManager::getFileIndex);
    }

    private boolean containsJava(final ProjectFileIndex projectFileIndex, final PsiDirectory[] dirs) {
        return Arrays.stream(dirs)
                     .filter(dir -> projectFileIndex.isUnderSourceRootOfType(dir.getVirtualFile(), JavaModuleSourceRootTypes.SOURCES))
                     .anyMatch(this::isJavaPackage);
    }

    // visible for testing
    boolean isJavaPackage(final PsiDirectory dir) {
        return JavaCreateTemplateInPackageAction.doCheckPackageExists(dir);
    }
}
