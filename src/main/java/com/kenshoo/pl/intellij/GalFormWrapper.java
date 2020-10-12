package com.kenshoo.pl.intellij;

import com.intellij.openapi.ui.DialogWrapper;
import com.kenshoo.pl.intellij.entities.EntityInput;
import com.kenshoo.pl.intellij.builders.EntityInputBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GalFormWrapper extends DialogWrapper {

    private final GalForm form;

    public GalFormWrapper() {
        super(true);
        this.form = new GalForm();
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