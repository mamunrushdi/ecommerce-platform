package org.example.swing;

import org.example.model.Product;
import org.example.swing.model.Cart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductCatalogFrame extends JFrame {
    private final DefaultListModel<Product> listModel = new DefaultListModel<>();
    private final JList<Product> productList = new JList<>(listModel);
    private final ApiClient apiClient = new ApiClient(); // API Gateway URL

    public ProductCatalogFrame() {
        setTitle("Product Catalog");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addToCartBtn = new JButton("Add to Cart");
        JButton viewCartBtn = new JButton("View Cart");
        JButton refreshBtn = new JButton("Refresh (Load from API)");

        JPanel bottom = new JPanel();
        bottom.add(addToCartBtn);
        bottom.add(viewCartBtn);
        bottom.add(refreshBtn);

        add(new JScrollPane(productList), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        addToCartBtn.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a product first.");
                return;
            }
            Cart.getInstance().addProduct(selected, 1);
            JOptionPane.showMessageDialog(this, selected.getName() + " added to cart.");
        });

        viewCartBtn.addActionListener(e -> new CartFrame().setVisible(true));

        refreshBtn.addActionListener(e -> loadProducts());
    }

    private void loadProducts() {
        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                // Call the API Gateway to fetchCheckout products
                return apiClient.fetchProducts();
            }

            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    listModel.clear();
                    for (Product p : products) {
                        listModel.addElement(p);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ProductCatalogFrame.this, "Error loading products: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }
}
