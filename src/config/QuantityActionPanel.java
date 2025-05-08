package config;

import javax.swing.*;
import java.awt.*;

public class QuantityActionPanel extends JPanel {
    public final JSpinner spinner;
    public final JButton button;

    public QuantityActionPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Container to stack button and spinner vertically
        JPanel vertical = new JPanel();
        vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
        vertical.setBackground(new Color(45, 45, 45));

        // Styled Button
        button = new JButton("Add to Cart");
        button.setBackground(new Color(60, 120, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stretch button vertically
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        button.setPreferredSize(new Dimension(120, 80));


        // Styled Spinner
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(100, 30));
        spinner.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ensure number is visible and background styled
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setOpaque(true);
            textField.setBackground(new Color(60, 60, 60));
            textField.setForeground(Color.WHITE);
            textField.setCaretColor(Color.WHITE);
            textField.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        spinner.setOpaque(true);

        // Add spacing and components
        vertical.add(button);
        vertical.add(Box.createVerticalStrut(10));
        vertical.add(spinner);

        add(vertical, BorderLayout.CENTER);
    }

    public int getQuantity() {
        return (Integer) spinner.getValue();
    }
}
