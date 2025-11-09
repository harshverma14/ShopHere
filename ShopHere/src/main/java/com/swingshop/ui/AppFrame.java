package com.swingshop.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.swingshop.model.User;
import com.swingshop.ui.components.NavButton;
import com.swingshop.ui.pages.CartPage;
import com.swingshop.ui.pages.DashboardPage;
import com.swingshop.ui.pages.OrdersPage;
import com.swingshop.ui.pages.ProductsPage;
import com.swingshop.ui.pages.ProfilePage;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private final CardLayout card = new CardLayout();
    private final JPanel cards = new JPanel(card);
        private final User user;


    public AppFrame(User user) {
        super("ShopHere — Hello, " + user.username);
        this.user = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 640);
        setLocationRelativeTo(null);

        // pages
        DashboardPage dashboard = new DashboardPage(user);
        ProductsPage products = new ProductsPage(user);
        CartPage cartPage = new CartPage(user);
        OrdersPage orders = new OrdersPage(user);
        ProfilePage profile = new ProfilePage(user);

        cards.add(dashboard, "dashboard");
        cards.add(products, "products");
        cards.add(cartPage, "cart");
        cards.add(orders, "orders");
        cards.add(profile, "profile");

        // sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0,1,0,8));
        sidebar.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        sidebar.add(new NavButton("Dashboard", e -> show("dashboard")));
        sidebar.add(new NavButton("Products", e -> show("products")));
        sidebar.add(new NavButton("Cart", e -> show("cart")));
        sidebar.add(new NavButton("Orders", e -> show("orders")));
        sidebar.add(new NavButton("Profile", e -> show("profile")));
        sidebar.add(new NavButton("Logout", e -> {
            dispose();
            new LoginFrame().setVisible(true);
        }));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, cards);
        split.setDividerLocation(200);
        split.setDividerSize(4);
        add(split);

        show("products");
    }

    private void show(String name) {
    card.show(cards, name);

    // Auto-refresh Cart or Orders pages when shown
    for (Component comp : cards.getComponents()) {
        try {
            if (name.equals("cart") && comp instanceof com.swingshop.ui.pages.CartPage cp) {
                var m = cp.getClass().getDeclaredMethod("refresh");
                m.setAccessible(true);
                m.invoke(cp);
            }
            if (name.equals("orders") && comp instanceof com.swingshop.ui.pages.OrdersPage op) {
                var m = op.getClass().getDeclaredMethod("load", int.class);
                m.setAccessible(true);
                m.invoke(op, user.id);
            }
        } catch (Exception ignored) {}
    }
}




    public static void setupTheme() {
        FlatDarkLaf.setup();
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Button.arc", 16);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("ScrollBar.showButtons", true);
    }
}
