package com.kenshoo.pl.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class PlEntityWizardAction extends AnAction {

  @Override
  public void update(AnActionEvent e) {
    // Using the event, evaluate the context, and enable or disable the action.
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    new GalFormWrapper().show();
    // Using the event, implement an action. For example, create and show a dialog.
  }

}

