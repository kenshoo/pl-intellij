package com.kenshoo.pl.intellij;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GalFormWrapper extends DialogWrapper {

    public GalFormWrapper() {
        super(true); // use current window as parent
        init();
        setTitle("Test DialogWrapper");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        GalForm form = new GalForm();
        return form.mainPanel;
    }
}