package config;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();

        comboBox.setBackground(Color.WHITE);
        comboBox.setOpaque(true);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createLineBorder(Utility.darkermiku, 1));
        comboBox.setFocusable(false);

        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", Utility.pink); 
        UIManager.put("ComboBox.selectionForeground", Color.BLACK);

        if (comboBox.isEditable()) {
            comboBox.setEditor(new BasicComboBoxEditor() {
                @Override
                protected JTextField createEditorComponent() {
                    JTextField editor = new JTextField();
                    editor.setBorder(null); 
                    editor.setBackground(Color.WHITE);  
                    editor.setForeground(Color.BLACK);
                    editor.setOpaque(true);
                    return editor;
                }
            });
        }
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(UIManager.getIcon("ComboBox.arrowButtonIcon")); 

        return button;
    }

    @Override
    protected ListCellRenderer<Object> createRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
                label.setOpaque(true);

                if (isSelected) {
                    label.setBackground(Utility.miku);
                }

                return label;
            }
        };
    }
}
