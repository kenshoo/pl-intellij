package com.kenshoo.pl.intellij.view;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiDirectory;
import com.kenshoo.pl.intellij.controller.NewEntityController;
import com.kenshoo.pl.intellij.model.EntityInput;
import com.kenshoo.pl.intellij.model.Language;
import com.kenshoo.pl.intellij.model.PLInputValidator;
import com.kenshoo.pl.intellij.model.ValidationError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

import static com.intellij.openapi.ui.Messages.showMessageDialog;
import static com.kenshoo.pl.intellij.model.Language.SCALA;


public class NewEntityFormWrapper extends DialogWrapper {

    private final PLInputValidator validator = PLInputValidator.INSTANCE;

    private final PsiDirectory directory;
    private final NewEntityForm form;
    private final NewEntityController controller;
    private final Language language;

    public NewEntityFormWrapper(@Nullable final PsiDirectory directory,
                                @NotNull final Language language) {
        super(true);
        this.controller = NewEntityController.INSTANCE;
        this.directory = directory;
        this.form = new NewEntityForm();
        init();
        this.language = language;
        setTitle("New PL Entity" + (language == SCALA ? " in Scala" : ""));
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return form.mainPanel;
    }

    @Override
    protected void doOKAction() {
        try {
            final EntityInput modelInput = toModelData();
            final Optional<ValidationError> error = validator.validate(modelInput).findFirst();

            if (error.isPresent()) {
                showMessageDialog(form.mainPanel, error.get().getMsg(), "Validation Check", null);
                return;
            }

            controller.createNewEntity(directory, modelInput);

            super.doOKAction();

        } catch (Throwable exception) {
            showMessageDialog(form.mainPanel, exception.getMessage() + "\n" + ExceptionUtils.getStackTrace(exception), "Unexpected Error", null);
        }
    }

    private EntityInput toModelData() {
        return new EntityInputBuilder()
                    .withEntityName(form.getEntityName())
                    .withTableName(form.getTableName())
                    .withFields(form.getFields())
                    .build();
    }
}