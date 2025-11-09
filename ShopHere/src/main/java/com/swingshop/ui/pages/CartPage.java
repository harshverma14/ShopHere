package com.swingshop.ui.pages;

import com.swingshop.model.Cart;
import com.swingshop.model.CartItem;
import com.swingshop.dao.OrderDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartPage extends JPanel {
    private final Cart cart = Shared.cart;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Product","Qty","Price","Line"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JLabel total = new JLabel();

    public CartPage(com.swingshop.model.User user) {
        setLayout(new BorderLayout());
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JButton remove = new JButton("Remove");
        JButton clear = new JButton("Clear");
        JButton checkout = new JButton("Checkout");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(total); bottom.add(remove); bottom.add(clear); bottom.add(checkout);
        add(bottom, BorderLayout.SOUTH);

        remove.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            String name = (String) model.getValueAt(row, 0);
            CartItem target = cart.all().stream().filter(ci -> ci.product.name.equals(name)).findFirst().orElse(null);
            if (target != null) { cart.remove(target.product.id); refresh(); }
        });
        clear.addActionListener(e -> { cart.clear(); refresh(); });
        checkout.addActionListener(e -> {
            try {
                if (cart.isEmpty()) { JOptionPane.showMessageDialog(this, "Cart is empty"); return; }
                int id = new OrderDAO().createOrder(user.id, cart);
                cart.clear();
                refresh();
                JOptionPane.showMessageDialog(this, "Order #" + id + " placed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        for (CartItem ci : cart.all()) {
            model.addRow(new Object[]{ci.product.name, ci.quantity, ci.product.price, ci.lineTotal()});
        }
        total.setText("Total: " + cart.total());
    }
}
