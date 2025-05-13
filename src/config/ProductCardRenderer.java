package config;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProductCardRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        String[] lines = value.toString().split("\n");
        String name = lines[0].trim();
        String price = lines[1].replace("₱", "").trim();
        String stock = lines[2].replace("Stock:", "").trim();
        String seller = lines[3].replace("Seller:", "").trim();

        ProductCardPanel panel = new ProductCardPanel();
        panel.setData(name, price, stock, seller);
        return panel;
    }
}
