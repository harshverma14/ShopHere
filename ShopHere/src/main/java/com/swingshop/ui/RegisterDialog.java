package com.swingshop.ui;

import com.swingshop.dao.UserDAO;
import com.swingshop.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {
    private final JTextField username = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final JPasswordField confirm = new JPasswordField(15);
    private final UserDAO userDAO = new UserDAO();

    public interface Listener { void onRegistered(User user); }

    public RegisterDialog(Frame owner, Listener listener) {
        super(owner, "Create account", true);
        setSize(380, 220);
        setLocationRelativeTo(owner);

        JButton submit = new JButton("Create account");
        JPanel p = new JPanel(new GridLayout(4,2,6,6));
        p.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        p.add(new JLabel("Username:")); p.add(username);
        p.add(new JLabel("Password:")); p.add(password);
        p.add(new JLabel("Confirm:")); p.add(confirm);
        p.add(new JLabel()); p.add(submit);
        add(p);

        submit.addActionListener(e -> {
            try {
                String u = username.getText().trim();
                String pw = new String(password.getPassword());
                String cf = new String(confirm.getPassword());
                if (u.isEmpty() || pw.isEmpty()) throw new RuntimeException("Please fill all fields");
                if (!pw.equals(cf)) throw new RuntimeException("Passwords do not match");
                if (userDAO.usernameExists(u)) throw new RuntimeException("Username already taken");
                User user = userDAO.register(u, pw);
                if (listener != null) listener.onRegistered(user);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
