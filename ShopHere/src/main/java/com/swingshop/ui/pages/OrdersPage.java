package com.swingshop.ui.pages;

import com.swingshop.dao.OrderDAO;
import com.swingshop.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class OrdersPage extends JPanel {
    private final DefaultListModel<String> model = new DefaultListModel<>();

    public OrdersPage(User user) {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Your Orders");
        title.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        title.setFont(title.getFont().deriveFont(16f));
        add(title, BorderLayout.NORTH);
        JList<String> list = new JList<>(model);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> load(user.id));
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT)); south.add(refresh);
        add(south, BorderLayout.SOUTH);

        load(user.id);
    }

    private void load(int userId) {
        try {
            model.clear();
            for (String row : new OrderDAO().listOrdersForUser(userId)) model.addElement(row);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
