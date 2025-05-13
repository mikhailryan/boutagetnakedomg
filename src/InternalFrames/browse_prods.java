/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import config.CustomScrollBarUI;
import config.ProductCardEditor;
import config.ProductCardRenderer;
import config.QuantityActionEditor;
import config.QuantityActionPanel;
import config.Utility;
import config.db_connector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mercy
 */
public class browse_prods extends javax.swing.JInternalFrame {

    /**
     * Creates new form browse_prods
     */
    public browse_prods() {
        initComponents();
        SwingUtilities.invokeLater(() -> getRootPane().requestFocus());
        setLabels();
        setBorder(search_bar);
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
        display_products();
        styleProductTable();
    }
    
    private void setLabels() {
        JLabel[] labels = {search_pic};
        String[] paths = {"search.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
        
    } 
    
    private void display_products() {
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.getData(
                "SELECT " +
                "p.name AS product_name, " +
                "p.price, " +
                "p.stock, " +
                "u.name AS seller_name " +
                "FROM products p " +
                "JOIN user u ON p.seller_id = u.id " +
                "ORDER BY p.seller_id DESC"
            );

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Product", "Action"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 1;
                }
            };

            while (result.next()) {
                String info = result.getString("product_name") +
                    "\n\n₱ " + result.getString("price") +
                    "\nStock: " + result.getString("stock") +
                    "\nSeller: " + result.getString("seller_name");

                model.addRow(new Object[]{info, ""});
            }

            products_table.setModel(model);
            
            SwingUtilities.invokeLater(() -> {
                products_table.getColumnModel().getColumn(0).setPreferredWidth(300); // or whatever value fits your layout
            });
            
        } catch (SQLException e) {
            System.out.println("Can't Load Products: " + e.getMessage());
        }

        JScrollBar verticalBar = productScrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBarUI());
        productScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {{
            setBackground(Utility.blackish);
        }});
    }

    private void styleProductTable() {
        products_table.setRowHeight(130);
        products_table.setShowGrid(false);
        products_table.setTableHeader(null);
        products_table.setBackground(Utility.blackish);
        products_table.setIntercellSpacing(new Dimension(0, 10));

        // Product Info (Column 0)
        products_table.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JTextArea area = new JTextArea(value.toString());
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            area.setEditable(false);
            area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            area.setForeground(Color.WHITE);
            area.setBackground(Utility.blackish);
            area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Utility.darkermiku, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            return area;
        });

        // Action Panel (Column 1)
        products_table.getColumnModel().getColumn(1).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> new QuantityActionPanel());
        products_table.getColumnModel().getColumn(1).setCellEditor(new QuantityActionEditor(products_table));
    }

    private void setBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 35, 0, 0) 
        ));
    }


// --- Button Renderer and Editor ---
//
//class QuantityActionPanel extends JPanel {
//    public JSpinner spinner;
//    public JButton button;
//
//    public QuantityActionPanel() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setOpaque(true);
//        setBackground(new Color(35, 35, 35));
//        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE)); // Allow vertical stretching
//
//        // --- Add to Cart Button ---
//        button = new JButton("Add");
//        button.setFocusPainted(false);
//        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        button.setBackground(new Color(30, 144, 255)); // Dodger blue
//        button.setForeground(Color.WHITE);
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 40)); // Stretch horizontally, fixed height
//
//        // --- Quantity Spinner ---
//        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
//        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
//        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
//        spinner.setMaximumSize(new Dimension(Short.MAX_VALUE, 30)); // Stretch horizontally
//
//        // --- Layout with vertical glue for spacing ---
//        add(Box.createVerticalGlue());
//        add(button);
//        add(Box.createRigidArea(new Dimension(0, 10)));
//        add(spinner);
//        add(Box.createVerticalGlue());
//    }
//}
//
//class QuantityRenderer implements TableCellRenderer {
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                                                   boolean hasFocus, int row, int column) {
//        return new QuantityActionPanel(); // Visual only
//    }
//}
//
//class QuantityActionEditor extends AbstractCellEditor implements TableCellEditor {
//    private QuantityActionPanel panel;
//    private int currentRow;
//
//    public QuantityActionEditor(JTable table) {
//        panel = new QuantityActionPanel();
//
//        panel.button.addActionListener(e -> {
//            int quantity = (Integer) panel.spinner.getValue();
//            String productInfo = table.getValueAt(currentRow, 0).toString().split("\n")[0];
//            JOptionPane.showMessageDialog(table, "Added to cart: " + productInfo + "\nQuantity: " + quantity);
//            fireEditingStopped(); // End editing
//        });
//    }
//
//    @Override
//    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
//                                                 int row, int column) {
//        currentRow = row;
//        return panel;
//    }
//
//    @Override
//    public Object getCellEditorValue() {
//        return null; // We don't need to return a value here
//    }
//}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        productScrollPane = new javax.swing.JScrollPane();
        products_table = new javax.swing.JTable();
        search_pic = new javax.swing.JLabel();
        search_bar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jPanel3 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(610, 430));

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 440));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        products_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        productScrollPane.setViewportView(products_table);

        jPanel1.add(productScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 610, 400));
        jPanel1.add(search_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 30, 30));

        search_bar.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        search_bar.setForeground(new java.awt.Color(153, 153, 153));
        search_bar.setText("Search");
        search_bar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                search_barFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                search_barFocusLost(evt);
            }
        });
        search_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_barActionPerformed(evt);
            }
        });
        jPanel1.add(search_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 200, 30));

        jPanel2.setBackground(new java.awt.Color(19, 122, 127));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        label1.setText("Refresh");
        jPanel2.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_barFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_barFocusGained
        if (search_bar.getText().trim().equals("Search")) {
            search_bar.setText("");
            search_bar.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_search_barFocusGained

    private void search_barFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_barFocusLost
        if (search_bar.getText().trim().isEmpty()) {
            search_bar.setText("Search");
            search_bar.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_search_barFocusLost

    private void search_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_barActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        display_products();
        styleProductTable();
    }//GEN-LAST:event_jPanel2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private java.awt.Label label1;
    private javax.swing.JScrollPane productScrollPane;
    private javax.swing.JTable products_table;
    private javax.swing.JTextField search_bar;
    private javax.swing.JLabel search_pic;
    // End of variables declaration//GEN-END:variables
}
