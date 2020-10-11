package com.kenshoo.pl.intellij;

import javax.swing.*;
import java.awt.*;

public class Test {

    public static void main(String[] args) {

        final Dimension SIZE = new Dimension(600, 400);


        GalForm mainForm = new GalForm();
        mainForm.mainPanel.setSize(SIZE);



        JFrame frame = new JFrame();
        frame.add(mainForm.mainPanel);
        frame.setSize(SIZE);
        frame.setLayout(null);
        frame.setVisible(true);

    }
}
