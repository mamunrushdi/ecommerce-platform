package org.example.swing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.Product;

@AllArgsConstructor
@Data
public class CartItem {
    private final Product product;
    private int quantity;
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
