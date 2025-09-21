package org.example.swing;


import org.example.swing.model.Cart;
import org.example.swing.model.CartItem;

import javax.swing.*;
import java.awt.*;

public class CartFrame extends JFrame {
    private final CartTableModel tableModel;
    private final JTable table;
    private final JLabel totalLabel;
    private final ApiClient apiClient = new ApiClient();
    public CartFrame() {
        setTitle("Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new CartTableModel();
        table = new JTable(tableModel);

        JButton increaseBtn = new JButton("Increase Qty");
        JButton decreaseBtn = new JButton("Decrease Qty");
        JButton removeBtn = new JButton("Remove");
        JButton checkoutBtn = new JButton("Checkout");
        JButton closeBtn = new JButton("Close");

        totalLabel = new JLabel("Total: $" + String.format("%.2f", Cart.getInstance().getTotal()));

        JPanel btnPanel = new JPanel();
        btnPanel.add(increaseBtn);
        btnPanel.add(decreaseBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(checkoutBtn);
        btnPanel.add(closeBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(totalLabel, BorderLayout.NORTH);

        increaseBtn.addActionListener(e -> changeQty(1));
        decreaseBtn.addActionListener(e -> changeQty(-1));
        removeBtn.addActionListener(e -> removeSelected());
        checkoutBtn.addActionListener(e -> doCheckout());
        closeBtn.addActionListener(e -> dispose());
    }

    private void changeQty(int delta) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an item first.");
            return;
        }
        CartItem it = tableModel.getItemAt(row);
        if (it == null) return;
        int newQty = it.getQuantity() + delta;
        if (newQty <= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Quantity will be 0 — remove item?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Cart.getInstance().removeProduct(it.getProduct().getId());
            } else return;
        } else {
            Cart.getInstance().updateQuantity(it.getProduct().getId(), newQty);
        }
        refresh();
    }

    private void removeSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an item first.");
            return;
        }
        CartItem it = tableModel.getItemAt(row);
        if (it == null) return;
        Cart.getInstance().removeProduct(it.getProduct().getId());
        refresh();
    }

//    private void doCheckout() {
//        if (Cart.getInstance().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Cart is empty.");
//            return;
//        }
//        int confirm = JOptionPane.showConfirmDialog(this,
//                "Total: $" + String.format("%.2f", Cart.getInstance().getTotal()) + "\nProceed to checkout?",
//                "Checkout",
//                JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            // TODO: Replace this stub with a REST call to Order Service / Billing Service.
//            // Example: call OrderService API, then BillingService API.
//            // For now we simulate an order id and clear the cart:
//            String fakeOrderId = "ORD-" + System.currentTimeMillis();
//            JOptionPane.showMessageDialog(this, "Order placed. Order ID: " + fakeOrderId);
//            Cart.getInstance().clear();
//            refresh();
//        }
//    }

    private void refresh() {
        tableModel.refresh();
        totalLabel.setText("Total: $" + String.format("%.2f", Cart.getInstance().getTotal()));
    }

    private void doCheckout() {
        if (Cart.getInstance().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Total: $" + String.format("%.2f", Cart.getInstance().getTotal()) + "\nProceed to checkout?",
                "Checkout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                private String errorMessage;

                @Override
                protected Void doInBackground() {
                    try {
                        apiClient.placeOrder(Cart.getInstance()); // call API Gateway
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    if (errorMessage != null) {
                        JOptionPane.showMessageDialog(CartFrame.this,
                                "Checkout failed: " + errorMessage);
                    } else {
                        JOptionPane.showMessageDialog(CartFrame.this,
                                "Order placed successfully!");
                        Cart.getInstance().clear();
                        refresh();
                    }
                }
            };
            worker.execute();
        }
    }
}
