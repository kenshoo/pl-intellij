package com.kenshoo.pl.intellij.view;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.kenshoo.pl.intellij.controller.NewEntityController;
import com.kenshoo.pl.intellij.model.EntityInput;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewEntityFormWrapper extends DialogWrapper {

    private final PsiJavaDirectoryImpl directory;
    private final NewEntityForm form;

    public NewEntityFormWrapper(@Nullable PsiJavaDirectoryImpl directory) {
        super(true);
        this.directory = directory;
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

            NewEntityController.INSTANCE.createNewEntity(directory, entityInput);

            super.actionPerformed(event);
        }
    }
}