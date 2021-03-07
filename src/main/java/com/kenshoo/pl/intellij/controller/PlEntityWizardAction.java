package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.kenshoo.pl.intellij.view.NewEntityFormWrapper;
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
        boolean available = sourcePackageClassifier.isJava(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(available);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Optional.ofNullable(LangDataKeys.IDE_VIEW.getData(e.getDataContext()))
                .map(IdeView::getOrChooseDirectory)
                .map(NewEntityFormWrapper::new)
                .ifPresent(NewEntityFormWrapper::show);
    }
}

