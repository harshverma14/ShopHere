package com.swingshop.ui.pages;

import com.swingshop.dao.UserDAO;
import com.swingshop.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {
    private final JPasswordField newPass = new JPasswordField(14);
    private final UserDAO userDAO = new UserDAO();

    public ProfilePage(User user) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.gridx=0; gc.gridy=0;
        add(new JLabel("Change Password:"), gc);
        gc.gridx=1; add(newPass, gc);
        gc.gridx=2; JButton save = new JButton("Update"); add(save, gc);

        save.addActionListener(e -> {
            try {
                String pw = new String(newPass.getPassword());
                if (pw.length() < 4) { JOptionPane.showMessageDialog(this, "Too short"); return; }
                userDAO.updatePassword(user.id, pw);
                JOptionPane.showMessageDialog(this, "Password updated");
                newPass.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
