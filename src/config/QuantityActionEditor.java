package config;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class QuantityActionEditor extends AbstractCellEditor implements TableCellEditor {
    private final QuantityActionPanel panel;
    private final JTable table;

    public QuantityActionEditor(JTable table) {
        this.table = table;
        this.panel = new QuantityActionPanel();

        panel.addButton.addActionListener(e -> {
            int row = table.getEditingRow();
            int quantity = getCommittedQuantity();

            // Handle adding to cart logic here
            System.out.println("Add to Cart: Row " + row + ", Quantity: " + quantity);

            // Reset to 1 after adding
            panel.quantitySpinner.setValue(1);

            fireEditingStopped();
        });

        customizeSpinner();
    }

    private void customizeSpinner() {
        JComponent editor = panel.quantitySpinner.getEditor();
        JFormattedTextField tf = (JFormattedTextField) editor.getComponent(0);
        tf.setBackground(Utility.grayish);
        tf.setForeground(Color.BLACK);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.quantitySpinner.setBorder(BorderFactory.createLineBorder(Utility.darkermiku));
        UIManager.put("Spinner.arrowButtonBackground", Utility.miku);
        UIManager.put("Spinner.arrowButtonBorder", BorderFactory.createLineBorder(Utility.darkermiku));
    }

    private int getCommittedQuantity() {
        try {
            panel.quantitySpinner.commitEdit();
        } catch (ParseException ex) {
            System.out.println("Commit failed: " + ex.getMessage());
        }
        return (int) panel.quantitySpinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }
}

