package com.swingshop.ui;

import com.swingshop.dao.UserDAO;
import com.swingshop.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("ShopHere — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);

        // Custom dark gradient background
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(45, 45, 45);
                Color c2 = new Color(20, 20, 20);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());
        background.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Glass effect panel (the main login box)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Welcome to ShopHere", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Username
        JLabel userLabel = new JLabel("Username:");
        styleLabel(userLabel);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(userLabel, gbc);
        styleTextField(username);
        gbc.gridx = 1; panel.add(username, gbc);

        // Password
        JLabel passLabel = new JLabel("Password:");
        styleLabel(passLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);
        styleTextField(password);
        gbc.gridx = 1; panel.add(password, gbc);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        styleButton(loginBtn, new Color(0x0096FF));
        styleButton(registerBtn, new Color(0x555555));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        background.add(panel);
        add(background);

        // Button listeners
        loginBtn.addActionListener(e -> doLogin());
        registerBtn.addActionListener(e ->
            new RegisterDialog(this, user ->
                JOptionPane.showMessageDialog(this, "Registered as " + user.username)
            ).setVisible(true)
        );
    }

    // 🔹 Helper methods for cleaner UI styling
    private void styleLabel(JLabel lbl) {
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setForeground(Color.LIGHT_GRAY);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setBackground(new Color(55, 55, 55));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Hover animation
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }

    private void doLogin() {
        try {
            String u = username.getText().trim();
            String p = new String(password.getPassword());
            User user = userDAO.findByUsernameAndPassword(u, p);
            if (user != null) {
                dispose();
                new AppFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
