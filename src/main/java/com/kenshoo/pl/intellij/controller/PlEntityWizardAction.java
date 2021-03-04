package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.view.NewEntityFormWrapper;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlEntityWizardAction extends AnAction {

    private final SourcePackageClassifier sourcePackageClassifier;

    public PlEntityWizardAction() {
        this(new SourcePackageClassifier());
    }

    PlEntityWizardAction(final SourcePackageClassifier sourcePackageClassifier) {
        this.sourcePackageClassifier = sourcePackageClassifier;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean available = isAvailable(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(available);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Optional.ofNullable(LangDataKeys.IDE_VIEW.getData(e.getDataContext()))
                .map(IdeView::getOrChooseDirectory)
                .map(NewEntityFormWrapper::new)
                .ifPresent(NewEntityFormWrapper::show);
    }

    private boolean isAvailable(DataContext dataContext) {
        final Optional<ProjectFileIndex> optionalProjectFileIndex =
            Optional.ofNullable(CommonDataKeys.PROJECT.getData(dataContext))
                    .map(ProjectRootManager::getInstance)
                    .map(ProjectRootManager::getFileIndex);

        final Optional<PsiDirectory[]> optionalDirs =
            Optional.ofNullable(LangDataKeys.IDE_VIEW.getData(dataContext))
                    .map(IdeView::getDirectories)
                    .filter(dirs -> !ArrayUtils.isEmpty(dirs));

        return optionalProjectFileIndex
            .flatMap(projectFileIndex -> optionalDirs.filter(dirs -> sourcePackageClassifier.containsJava(projectFileIndex, dirs)))
            .isPresent();
    }
}

