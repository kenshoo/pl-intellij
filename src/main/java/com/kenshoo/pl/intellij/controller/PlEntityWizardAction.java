package com.kenshoo.pl.intellij.controller;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class PlEntityWizardAction extends AnAction {

  @Override
  public void update(AnActionEvent e) {
    // Using the event, evaluate the context, and enable or disable the action.
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    new NewEntityFormWrapper().show();
    PsiFile data = e.getData(LangDataKeys.PSI_FILE);
    // Using the event, implement an action. For example, create and show a dialog.
  }

}
