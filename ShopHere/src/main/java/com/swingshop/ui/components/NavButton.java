package com.swingshop.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NavButton extends JButton {
    public NavButton(String text, ActionListener onClick) {
        super(text);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
        setFont(getFont().deriveFont(Font.PLAIN, 14f));
        addActionListener(onClick);
    }
}
