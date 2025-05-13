package config;

import javax.swing.*;
import java.awt.*;

public class QuantityActionPanel extends JPanel {
    public final JSpinner quantitySpinner;
    public final JButton addButton;

    public QuantityActionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        quantitySpinner.setMaximumSize(new Dimension(100, 35));
        quantitySpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeSpinner();

        addButton = new JButton("Add to Cart");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeButton();

        add(Box.createVerticalGlue());
        add(addButton);
        add(Box.createVerticalStrut(10));
        add(quantitySpinner);
        add(Box.createVerticalGlue());
    }

    private void customizeButton() {
        addButton.setBackground(Utility.miku);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(140, 35));
        addButton.setMaximumSize(new Dimension(160, 40));
        addButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Utility.darkermiku, 2, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(Utility.darkermiku);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(Utility.miku);
            }
        });
    }

    private void customizeSpinner() {
        JComponent editor = quantitySpinner.getEditor();
        JFormattedTextField tf = (JFormattedTextField) editor.getComponent(0);
        tf.setBackground(Utility.grayish);
        tf.setForeground(Color.BLACK);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        quantitySpinner.setBorder(BorderFactory.createLineBorder(Utility.darkermiku, 2, true));
    }
}
