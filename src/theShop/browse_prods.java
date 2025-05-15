/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import Dialogs.add_to_cart;
import config.CustomComboBoxUI;
import config.CustomScrollBarUI;
import config.Utility;
import config.db_connector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import theShop.Reseeller;

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
        
        populateSellerComboBox();
        String keyword = search_bar.getText().trim();
        if (keyword.equals("Search")) keyword = "";

        display_products(keyword, sellers_filter.getSelectedItem().toString());
        styleProductTable();
    }
    
    private void populateSellerComboBox() {
        sellers_filter.removeAllItems(); // Clear existing items
        sellers_filter.addItem("All"); // Default option to show all products

        try {
            db_connector dbcon = new db_connector();
            ResultSet rs = dbcon.getData("SELECT DISTINCT u.name FROM user u JOIN products p ON u.id = p.seller_id");

            while (rs.next()) {
                sellers_filter.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error loading sellers: " + e.getMessage());
        }
    }
    
    private void setLabels() {
        JLabel[] labels = {search_pic};
        String[] paths = {"search.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
        
    } 
    
    private void display_products(String keyword, String sellerFilter) {
        try {
            db_connector dbcon = new db_connector();
            String sql = "SELECT p.id, p.name AS product_name, p.price, p.stock, u.name AS seller_name " +
                         "FROM products p " +
                         "JOIN user u ON p.seller_id = u.id ";

            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
            boolean hasSellerFilter = sellerFilter != null && !sellerFilter.equals("All");

            if (hasKeyword || hasSellerFilter) {
                sql += "WHERE ";
                if (hasKeyword) {
                    sql += "p.name LIKE '%" + keyword + "%' ";
                    if (hasSellerFilter) sql += "AND ";
                }
                if (hasSellerFilter) {
                    sql += "u.name = '" + sellerFilter + "' ";
                }
            }

            sql += "ORDER BY p.seller_id DESC";
            ResultSet result = dbcon.getData(sql);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Product", "Action", "productId"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 1;
                }
            };

            boolean has_results = false;

            while (result.next()) {
                has_results = true;
                int productId = result.getInt("id");
                String info = result.getString("product_name") +
                    System.lineSeparator() + System.lineSeparator() + 
                    "₱ " + String.format("%.2f", result.getDouble("price")) +
                    System.lineSeparator() + "Stock: " + result.getInt("stock") +
                    System.lineSeparator() + "Seller: " + result.getString("seller_name");

                model.addRow(new Object[]{info, "Add", productId});
            }

            if (!has_results) {
                model.addRow(new Object[]{"No products found", "", ""});
            }

            products_table.setModel(model);

            // Hide "Action" column if no products
            if (!has_results) {
                products_table.getColumnModel().getColumn(1).setMinWidth(0);
                products_table.getColumnModel().getColumn(1).setMaxWidth(0);
                products_table.getColumnModel().getColumn(1).setWidth(0);
            } else {
                products_table.getColumnModel().getColumn(1).setMinWidth(100);
                products_table.getColumnModel().getColumn(1).setMaxWidth(120);
                products_table.getColumnModel().getColumn(1).setPreferredWidth(170);
            }

            styleProductTable();

            SwingUtilities.invokeLater(() -> {
                products_table.getColumnModel().getColumn(0).setPreferredWidth(410);
                products_table.getColumnModel().getColumn(2).setMinWidth(0);
                products_table.getColumnModel().getColumn(2).setMaxWidth(0);
                products_table.getColumnModel().getColumn(2).setWidth(0);
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

        // Product Info Column (0)
        products_table.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Utility.blackish);
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Utility.darkermiku, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            if (value != null) {
                String[] lines = value.toString().split(System.lineSeparator());

                if (lines.length >= 4) {
                    JLabel name = new JLabel(lines[0]);
                    name.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    name.setForeground(Color.WHITE);

                    JLabel price = new JLabel(lines[2]);
                    price.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    price.setForeground(new Color(0, 255, 180));

                    JLabel stock = new JLabel(lines[3]);
                    stock.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    stock.setForeground(Color.LIGHT_GRAY);

                    JLabel seller = new JLabel(lines[4]);
                    seller.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                    seller.setForeground(Color.GRAY);

                    panel.add(name);
                    panel.add(Box.createVerticalStrut(5));
                    panel.add(price);
                    panel.add(stock);
                    panel.add(seller);
                } else {
                    JLabel fallback = new JLabel(value.toString());
                    fallback.setForeground(Color.WHITE);
                    panel.add(fallback);
                }
            }

            return panel;
        });

        // Action Column (1)
        products_table.getColumnModel().getColumn(1).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            String info = table.getValueAt(row, 0).toString();
            if (info.equalsIgnoreCase("No products found")) return new JLabel();

            JButton button = new JButton();
            boolean outOfStock = info.contains("Stock: 0");

            button.setText(outOfStock ? "Out of Stock" : "Add to Cart");
            button.setEnabled(!outOfStock);
            button.setBackground(outOfStock ? Color.GRAY : Utility.miku);
            button.setForeground(Color.BLACK);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            return button;
        });

        products_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final JButton button = new JButton("Add to Cart");

            {
                button.setBackground(Utility.miku);
                button.setForeground(Color.BLACK);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.addActionListener(e -> {
                    int row = products_table.getSelectedRow();
                    String info = products_table.getValueAt(row, 0).toString();

                    if (info.equalsIgnoreCase("No products found") || info.contains("Stock: 0")) {
                        fireEditingStopped();
                        return;
                    }

                    int productId = (int) products_table.getValueAt(row, 2);
                    add_to_cart cart = new add_to_cart((Frame) SwingUtilities.getWindowAncestor(products_table), true);
                    cart.setProductId(productId);
                    cart.pack();
                    JDesktopPane desktopPane = ((Reseeller) SwingUtilities.getWindowAncestor(products_table)).getDesktopPane();
                    cart.setLocationRelativeTo(desktopPane);
                    cart.setVisible(true);
                    fireEditingStopped();
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                String info = table.getValueAt(row, 0).toString();
                if (info.equalsIgnoreCase("No products found") || info.contains("Stock: 0")) return new JLabel();
                return button;
            }
        });
    }


    private void setBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 35, 0, 0) 
        ));
    }

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
        sellers_filter = new javax.swing.JComboBox<>();
        sellers_filter.setUI(new CustomComboBoxUI());

        setPreferredSize(new java.awt.Dimension(610, 470));

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

        jPanel1.add(productScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 610, 350));
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
        search_bar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_barKeyReleased(evt);
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

        sellers_filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sellers_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellers_filterActionPerformed(evt);
            }
        });
        jPanel1.add(sellers_filter, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
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
        search_bar.setText(""); // Clear search
        sellers_filter.setSelectedItem("All"); // Reset seller filter
        display_products("", "All");
        styleProductTable();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void search_barKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_barKeyReleased
        String keyword = search_bar.getText().trim();
        if (keyword.equalsIgnoreCase("Search")) keyword = "";
        display_products(keyword, sellers_filter.getSelectedItem().toString());
    }//GEN-LAST:event_search_barKeyReleased

    private void sellers_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellers_filterActionPerformed
        try {
            if (search_bar == null) {
                System.out.println("search_bar is null!");
                return;
            }

            if (sellers_filter == null) {
//                System.out.println("sellers_filter is null!");
                return;
            }

            String keyword = search_bar.getText().trim();
            if (keyword.equalsIgnoreCase("Search")) keyword = "";

            Object selectedItem = sellers_filter.getSelectedItem();
            if (selectedItem == null) {
//                System.out.println("Selected seller is null!");
                return;
            }

            display_products(keyword, selectedItem.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_sellers_filterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private java.awt.Label label1;
    private javax.swing.JScrollPane productScrollPane;
    private javax.swing.JTable products_table;
    private javax.swing.JTextField search_bar;
    private javax.swing.JLabel search_pic;
    private javax.swing.JComboBox<String> sellers_filter;
    // End of variables declaration//GEN-END:variables
}
