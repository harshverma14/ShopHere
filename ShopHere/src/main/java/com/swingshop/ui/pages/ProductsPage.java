package com.swingshop.ui.pages;

import com.swingshop.dao.ProductDAO;
import com.swingshop.model.Product;
import com.swingshop.model.User;
import com.swingshop.model.Cart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductsPage extends JPanel {
    private final ProductDAO productDAO = new ProductDAO();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Price","Stock"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JSpinner qty = new JSpinner(new SpinnerNumberModel(1,1,999,1));
    private final Cart cart = com.swingshop.ui.pages.Shared.cart;

    public ProductsPage(User user) {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refresh = new JButton("Refresh");
        JButton add = new JButton("Add to cart");
        top.add(new JLabel("Qty:")); top.add(qty); top.add(add); top.add(refresh);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh.addActionListener(e -> load());
        add.addActionListener(e -> addSelected());

        load();
    }

    private void load() {
        try {
            model.setRowCount(0);
            List<Product> list = productDAO.listAll();
            for (Product p : list) model.addRow(new Object[]{p.id, p.name, p.price, p.stock});
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSelected() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a product"); return; }
        int id = (int) model.getValueAt(row, 0);
        int q = (int) qty.getValue();
        try {
            Product p = new ProductDAO().byId(id);
            if (p == null) { JOptionPane.showMessageDialog(this, "Not found"); return; }
            if (q > p.stock) { JOptionPane.showMessageDialog(this, "Only " + p.stock + " in stock"); return; }
            cart.add(p, q);
            JOptionPane.showMessageDialog(this, "Added to cart");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
