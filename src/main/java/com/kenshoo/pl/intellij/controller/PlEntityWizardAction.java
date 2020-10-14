package com.kenshoo.pl.intellij.controller;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.view.NewEntityFormWrapper;
import org.jetbrains.annotations.NotNull;

public class PlEntityWizardAction extends AnAction {

  @Override
  public void update(AnActionEvent e) {
    final Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);
    e.getPresentation().setEnabledAndVisible(navigatable instanceof PsiJavaDirectoryImpl);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    final PsiJavaDirectoryImpl javaDirectory = (PsiJavaDirectoryImpl) e.getData(CommonDataKeys.NAVIGATABLE);
    new NewEntityFormWrapper(javaDirectory).show();
  }

}

