package com.swingshop.ui.pages;

import com.swingshop.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JPanel {
    public DashboardPage(User user) {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Welcome, " + user.username + " — Dashboard");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        lbl.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(lbl, BorderLayout.NORTH);

        JTextArea info = new JTextArea("Quick tips:\n• Use the sidebar to navigate\n• Products page to shop\n• Cart to checkout\n• Orders to review purchases");
        info.setEditable(false);
        add(new JScrollPane(info), BorderLayout.CENTER);
    }
}
