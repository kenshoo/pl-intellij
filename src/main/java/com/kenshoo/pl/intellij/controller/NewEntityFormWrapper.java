package com.kenshoo.pl.intellij.controller;

import com.intellij.openapi.ui.DialogWrapper;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.EntityInputBuilder;
import com.kenshoo.pl.intellij.view.NewEntityForm;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewEntityFormWrapper extends DialogWrapper {

    private final NewEntityForm form;

    public NewEntityFormWrapper() {
        super(true);
        this.form = new NewEntityForm();
        this.myOKAction = new OkWrapper();
        init();
        setTitle("Test DialogWrapper");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return form.mainPanel;
    }


    public class OkWrapper extends DialogWrapper.OkAction {

        @Override
        public void actionPerformed(ActionEvent event) {
            final EntityInput entityInput = new EntityInputBuilder()
                    .withEntityName(form.getEntityName())
                    .withTableName(form.getTableName())
                    .withFields(form.getFields())
                    .build();

            super.actionPerformed(event);
        }
    }
}