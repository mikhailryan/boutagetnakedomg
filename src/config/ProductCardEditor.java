/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class ProductCardEditor extends AbstractCellEditor implements TableCellEditor {
    private ProductCardPanel panel;

    public ProductCardEditor() {
        panel = new ProductCardPanel();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        String[] lines = value.toString().split("\n");
        panel.setData(
            lines[0].trim(),
            lines[1].replace("₱", "").trim(),
            lines[2].replace("Stock:", "").trim(),
            lines[3].replace("Seller:", "").trim()
        );
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null; // You can return edited data here if needed
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
