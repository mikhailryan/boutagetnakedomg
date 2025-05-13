/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ProductCardPanel extends JPanel {
    private JLabel nameLabel, priceLabel, stockLabel, sellerLabel;
    public JButton addToCartButton;
    public JSpinner quantitySpinner;

    public ProductCardPanel() {
        setLayout(new BorderLayout(10, 0));
        setBackground(Utility.blackish);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.miku, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Utility.blackish);

        nameLabel = createLabel(Font.BOLD, 14, Color.WHITE);
        priceLabel = createLabel(Font.PLAIN, 12, Utility.grayish);
        stockLabel = createLabel(Font.PLAIN, 12, Utility.grayish);
        sellerLabel = createLabel(Font.PLAIN, 12, Utility.grayish);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);
        infoPanel.add(sellerLabel);

        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        actionPanel.setBackground(Utility.blackish);

        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(Utility.miku);
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addToCartButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        addToCartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

        actionPanel.add(addToCartButton);
        actionPanel.add(quantitySpinner);

        add(infoPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.EAST);
    }

    private JLabel createLabel(int style, int size, Color color) {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    public void setData(String name, String price, String stock, String seller) {
        nameLabel.setText(name);
        priceLabel.setText("₱ " + price);
        stockLabel.setText("Stock: " + stock);
        sellerLabel.setText("Seller: " + seller);
    }
}
