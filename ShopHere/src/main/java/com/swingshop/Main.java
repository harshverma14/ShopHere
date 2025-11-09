package com.swingshop;

import com.formdev.flatlaf.FlatDarkLaf;
import com.swingshop.ui.AppFrame;
import com.swingshop.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame.setupTheme();
            new LoginFrame().setVisible(true);
        });
    }
}
