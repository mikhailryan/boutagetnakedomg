package config;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class QuantityActionEditor extends AbstractCellEditor implements TableCellEditor {
    private QuantityActionPanel panel;
    private int currentRow;
    private JTable table;

    public QuantityActionEditor(JTable tableRef) {
        this.table = tableRef;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.currentRow = row;
        panel = new QuantityActionPanel(); // interactive panel

        panel.button.addActionListener(e -> {
            int qty = panel.getQuantity();
            String product = table.getValueAt(currentRow, 0).toString().split("\n")[0];

            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(table),
                "Added to cart:\n" + product + "\nQty: " + qty,
                "Cart Update",
                JOptionPane.INFORMATION_MESSAGE
            );
            fireEditingStopped();
        });

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Add";
    }
}
