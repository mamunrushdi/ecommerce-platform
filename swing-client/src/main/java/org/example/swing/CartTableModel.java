package org.example.swing;

import org.example.swing.model.Cart;
import org.example.swing.model.CartItem;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CartTableModel extends AbstractTableModel {
    private final String[] columns = {"Product", "Unit price", "Quantity", "Total"};
    private List<CartItem> items;

    public CartTableModel() {
        refresh();
    }

    public void refresh() {
        items = Cart.getInstance().getItems();
        fireTableDataChanged();
    }

    public CartItem getItemAt(int row) {
        if (row < 0 || row >= items.size()) return null;
        return items.get(row);
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CartItem it = items.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return it.getProduct().getName();
            case 1:
                return String.format("%.2f", it.getProduct().getPrice());
            case 2:
                return it.getQuantity();
            case 3:
                return String.format("%.2f", it.getTotalPrice());
            default:
                return "";
        }
    }
}
