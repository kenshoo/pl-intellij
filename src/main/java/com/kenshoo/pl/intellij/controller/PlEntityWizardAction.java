package com.kenshoo.pl.intellij.controller;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.classifier.ModuleClassifier;
import com.kenshoo.pl.intellij.classifier.SourcePackageClassifier;
import com.kenshoo.pl.intellij.model.Language;
import com.kenshoo.pl.intellij.view.NewEntityFormWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlEntityWizardAction extends AnAction {

    private final SourcePackageClassifier sourcePackageClassifier;
    private final ModuleClassifier moduleClassifier;

    public PlEntityWizardAction() {
        this(new SourcePackageClassifier(),
             new ModuleClassifier());
    }

    PlEntityWizardAction(final SourcePackageClassifier sourcePackageClassifier,
                         final ModuleClassifier moduleClassifier) {
        this.sourcePackageClassifier = sourcePackageClassifier;
        this.moduleClassifier = moduleClassifier;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean available = sourcePackageClassifier.isJava(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(available);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();

        Optional.ofNullable(LangDataKeys.IDE_VIEW.getData(dataContext))
                .map(IdeView::getOrChooseDirectory)
                .map(dir -> newEntityFormWrapper(dir, moduleClassifier.determineLanguage(dataContext)))
                .ifPresent(NewEntityFormWrapper::show);
    }

    // Visible for testing
    NewEntityFormWrapper newEntityFormWrapper(final PsiDirectory dir, final Language language) {
        return new NewEntityFormWrapper(dir, language);
    }
}

