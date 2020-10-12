package com.kenshoo.pl.intellij;

import com.kenshoo.pl.intellij.view.NewEntityForm;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args) {

        final Dimension SIZE = new Dimension(600, 400);


        NewEntityForm mainForm = new NewEntityForm();
        mainForm.mainPanel.setSize(SIZE);



        JFrame frame = new JFrame();
        frame.add(mainForm.mainPanel);
        frame.setSize(SIZE);
        frame.setLayout(null);
        frame.setVisible(true);

    }
}
