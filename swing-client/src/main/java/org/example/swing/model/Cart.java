package org.example.swing.model;


import org.example.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private static final Cart INSTANCE = new Cart();
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    private Cart() {
    }

    public static Cart getInstance() {
        return INSTANCE;
    }

    public synchronized void addProduct(Product product, int qty) {
        if (product == null) {
            return;
        }
        CartItem it = items.get(product.getId());
        if (it == null) {
            items.put(product.getId(), new CartItem(product, Math.max(1, qty)));
        } else {
            it.setQuantity(it.getQuantity());
        }
    }
    public synchronized void updateQuantity(String productId, int qty) {
        CartItem it = items.get(productId);
        if (it == null) return;
        if (qty <= 0) items.remove(productId);
        else it.setQuantity(qty);
    }

    public synchronized void removeProduct(String productId) {
        items.remove(productId);
    }

    public synchronized List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public synchronized double getTotal() {
        return items.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public synchronized void clear() {
        items.clear();
    }

    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }
}
