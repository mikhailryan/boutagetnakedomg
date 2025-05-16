/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import Dialogs.CustomMessageDialog;
import Dialogs.CustomYesNoDialog;
import config.OrderReceiptGenerator;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Mercy
 */
public class cart_content extends javax.swing.JInternalFrame {

    /**
     * Creates new form cart_content
     */
    public cart_content() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
        display_cart();
    }
    
    private void display_cart() {
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.getData(
                "SELECT c.id AS cart_id, p.name, c.quantity, p.price, (c.quantity * p.price) AS subtotal " +
                "FROM cart c JOIN products p ON c.product_id = p.id " +
                "WHERE c.user_id = " + Session.getInstance().getUserId()
            );

            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Product", "Qty", "Price", "Subtotal", "", "cartId", "", ""}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 4 || column == 6 || column == 7;
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 4 || columnIndex == 6 || columnIndex == 7) return Icon.class;
                    return Object.class;
                }
            };

            boolean hasItems = false;
            while (result.next()) {
                hasItems = true;

                String name = result.getString("name");
                int qty = result.getInt("quantity");
                double price = result.getDouble("price");
                double subtotal = result.getDouble("subtotal");
                int cartId = result.getInt("cart_id");

                model.addRow(new Object[]{
                    name,
                    qty,
                    String.format("₱%.2f", price),
                    String.format("₱%.2f", subtotal),
                    resizeImage("/images/trash.png", 24),
                    cartId,
                    resizeImage("/images/minus.png", 18),
                    resizeImage("/images/plus.png", 18)
                });
            }

            if (!hasItems) {
                model.addRow(new Object[]{"Your cart is empty", "", "", "", null, null, null, null});
                cart_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                   boolean hasFocus, int row, int column) {
                        JLabel label = new JLabel(value != null ? value.toString() : "");
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setFont(new Font("SansSerif", Font.ITALIC, 14));
                        label.setForeground(Color.GRAY);
                        label.setOpaque(true);
                        label.setBackground(Utility.blackish);
                        return label;
                    }
                });
                cart_table.setRowSelectionAllowed(false);
            }

            // Calculate total
            double total = 0.0;
            result.beforeFirst();
            while (result.next()) {
                total += result.getDouble("subtotal");
            }
            total_price.setText("Total: ₱" + String.format("%.2f", total));

            cart_table.setModel(model);

            // Column sizing
            cart_table.getColumnModel().getColumn(0).setPreferredWidth(180); // Product
            cart_table.getColumnModel().getColumn(1).setPreferredWidth(50);  // Qty
            cart_table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
            cart_table.getColumnModel().getColumn(3).setPreferredWidth(100); // Subtotal
            cart_table.getColumnModel().getColumn(4).setPreferredWidth(40);  // Trash
            cart_table.getColumnModel().getColumn(6).setPreferredWidth(30);  // Decrease
            cart_table.getColumnModel().getColumn(7).setPreferredWidth(30);  // Increase

            // Set blank headers for icons
            cart_table.getColumnModel().getColumn(4).setHeaderValue("");
            cart_table.getColumnModel().getColumn(6).setHeaderValue("");
            cart_table.getColumnModel().getColumn(7).setHeaderValue("");

            // Header styling
            JTableHeader header = cart_table.getTableHeader();
            for (int i = 0; i < cart_table.getColumnCount(); i++) {
                cart_table.getColumnModel().getColumn(i).setHeaderRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                   boolean hasFocus, int row, int column) {
                        JLabel label = new JLabel(value != null ? value.toString() : "");
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setFont(new Font("SansSerif", Font.BOLD, 13));
                        label.setOpaque(true);
                        label.setBackground(Utility.darkermiku);
                        label.setForeground(Color.WHITE);
                        return label;
                    }
                });
            }

            // 🔒 Remove previous listeners to avoid duplicates
            for (MouseListener ml : cart_table.getMouseListeners()) {
                cart_table.removeMouseListener(ml);
            }

            // 🖱️ Mouse listener for remove/increase/decrease
            cart_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = cart_table.rowAtPoint(e.getPoint());
                    int col = cart_table.columnAtPoint(e.getPoint());

                    if (row < 0 || col < 0 || cart_table.getValueAt(row, 5) == null) return;

                    int cartId = (int) cart_table.getValueAt(row, 5);
                    int currentQty = (int) cart_table.getValueAt(row, 1);

                    if (col == 4) { // Trash
                        boolean confirm = CustomYesNoDialog.showConfirm(null, "Remove this item from cart?", "Confirm");
                        if (confirm) {
                            db_connector.updateDatabase("DELETE FROM cart WHERE id = " + cartId);
                            display_cart();
                        }
                    } else if (col == 6) { // Decrease
                        if (currentQty > 1) {
                            db_connector.updateDatabase("UPDATE cart SET quantity = quantity - 1 WHERE id = " + cartId);
                            display_cart();
                        } else {
                            boolean confirm = CustomYesNoDialog.showConfirm(null, "Quantity is 1. Remove this item?", "Confirm");
                            if (confirm) {
                                db_connector.updateDatabase("DELETE FROM cart WHERE id = " + cartId);
                                display_cart();
                            }
                        }
                    } else if (col == 7) { // Increase
                        try {
                            db_connector conn = new db_connector();
                            ResultSet stockResult = conn.getData(
                                "SELECT p.stock FROM cart c JOIN products p ON c.product_id = p.id WHERE c.id = " + cartId
                            );

                            if (stockResult.next()) {
                                int stock = stockResult.getInt("stock");

                                if (currentQty < stock) {
                                    db_connector.updateDatabase("UPDATE cart SET quantity = quantity + 1 WHERE id = " + cartId);
                                    display_cart();
                                } else {
                                    CustomMessageDialog.showError(null,
                                        "Cannot add more. Only " + stock + " item(s) available in stock.",
                                        "Stock Limit Reached"
                                    );
                                }
                            }
                        } catch (SQLException ex) {
                            System.out.println("Error checking stock: " + ex.getMessage());
                        }
                    }
                }
            });

            SwingUtilities.invokeLater(this::styleCartTable);

        } catch (SQLException e) {
            System.out.println("Can't Load Cart: " + e.getMessage());
        }
    }


    
    private void styleCartTable() {
        cart_table.setRowHeight(40);
        cart_table.setShowGrid(false);
        cart_table.setTableHeader(null);
        cart_table.setBackground(Utility.blackish);
        cart_table.setForeground(Color.WHITE);
        cart_table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cart_table.setIntercellSpacing(new Dimension(0, 8));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setForeground(Color.WHITE);
        cellRenderer.setBackground(Utility.blackish);
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < 4; i++) {
            cart_table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Trash
        cart_table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                if (value instanceof Icon) label.setIcon((Icon) value);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label.setOpaque(true);
                label.setBackground(Utility.blackish);
                return label;
            }
        });

        // Hide cartId
        cart_table.getColumnModel().getColumn(5).setMinWidth(0);
        cart_table.getColumnModel().getColumn(5).setMaxWidth(0);
        cart_table.getColumnModel().getColumn(5).setWidth(0);

        // Decrease button
        cart_table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value instanceof Icon) label.setIcon((Icon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });

        // Increase button
        cart_table.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value instanceof Icon) label.setIcon((Icon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });
    }


    
    public static ImageIcon resizeImage(String path, int size) {
        ImageIcon icon = new ImageIcon(Utility.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    
    private void orderCart() {
        db_connector db = new db_connector();
        Connection conn = null;

        try {
            int userId = Session.getInstance().getUserId();
            conn = db.getConnection(); // Assumes db_connector has this method
            conn.setAutoCommit(false); // Start transaction

            ResultSet cartItems = db.getData("SELECT * FROM cart WHERE user_id = " + userId);
            if (!cartItems.next()) {
                CustomMessageDialog.showError(null, "Cart is empty!", "Order Cart");
                return;
            }
            cartItems.beforeFirst();

            List<Map<String, Object>> items = new ArrayList<>();

            while (cartItems.next()) {
                int qty = cartItems.getInt("quantity");
                int productId = cartItems.getInt("product_id");

                ResultSet product = db.getData("SELECT name, price, seller_id FROM products WHERE id = " + productId);
                if (product.next()) {
                    String name = product.getString("name");
                    double price = product.getDouble("price");
                    int sellerId = product.getInt("seller_id");
                    double subtotal = qty * price;

                    Map<String, Object> item = new HashMap<>();
                    item.put("product_id", productId);
                    item.put("name", name);
                    item.put("qty", qty);
                    item.put("price", price);
                    item.put("subtotal", subtotal);
                    item.put("seller_id", sellerId);
                    items.add(item);
                }
            }

            double total = items.stream().mapToDouble(i -> (double) i.get("subtotal")).sum();

            boolean confirm = CustomYesNoDialog.showConfirm(null,
                "Confirm order with a total of ₱" + String.format("%.2f", total) + "?",
                "Confirm Order");

            if (!confirm) return;

            // Insert order
            String insertOrder = "INSERT INTO orders (reseller_id, total_amount, order_date) VALUES (?, ?, NOW())";
            PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, total);
            orderStmt.executeUpdate();

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                conn.rollback();
                CustomMessageDialog.showError(null, "Failed to get generated order ID.", "Order Cart");
                return;
            }
            int orderId = generatedKeys.getInt(1);

            // Insert order items and deduct stock
            String insertItem = "INSERT INTO order_items (order_id, seller_id, product_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(insertItem);

            for (Map<String, Object> item : items) {
                int productId = (int) item.get("product_id");
                int qty = (int) item.get("qty");

                // 1. Check current stock
                ResultSet stockCheck = db.getData("SELECT stock FROM products WHERE id = " + productId);
                if (stockCheck.next()) {
                    int currentStock = stockCheck.getInt("stock");
                    if (qty > currentStock) {
                        conn.rollback();
                        CustomMessageDialog.showError(null, "Not enough stock for product: " + item.get("name"), "Stock Error");
                        return;
                    }

                    // 2. Deduct stock
                    PreparedStatement stockUpdate = conn.prepareStatement("UPDATE products SET stock = stock - ? WHERE id = ?");
                    stockUpdate.setInt(1, qty);
                    stockUpdate.setInt(2, productId);
                    stockUpdate.executeUpdate();
                } else {
                    conn.rollback();
                    CustomMessageDialog.showError(null, "Product not found: " + item.get("name"), "Stock Error");
                    return;
                }

                // 3. Insert order item
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, (int) item.get("seller_id"));
                itemStmt.setInt(3, productId);
                itemStmt.setInt(4, qty);
                itemStmt.setDouble(5, (double) item.get("price"));
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();

            PreparedStatement clearCart = conn.prepareStatement("DELETE FROM cart WHERE user_id = ?");
            clearCart.setInt(1, userId);
            clearCart.executeUpdate();

            // Commit transaction
            conn.commit();

            String receiptPath = OrderReceiptGenerator.generateReceipt(orderId, Session.getInstance().getUsername(), items, total);

            // Ask the user if they want to open it
            boolean view = CustomYesNoDialog.showConfirm(null, "Order placed successfully!\nDo you want to view the receipt now?", "Receipt");

            if (view) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(new File(receiptPath));
                    } catch (IOException ex) {
                        CustomMessageDialog.showError(null, "Could not open receipt: " + ex.getMessage(), "View Error");
                    }
                } else {
                    CustomMessageDialog.showError(null, "This system doesn't support opening files automatically.", "Unsupported");
                }
            }
            
            display_cart();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            CustomMessageDialog.showError(null, "Order failed: " + e.getMessage(), "Order Cart");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // Reset autocommit
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        cart_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        total_price = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(610, 470));

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cart_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(cart_table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 610, 340));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel2MouseReleased(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Order Cart");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 120, 40));

        total_price.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        total_price.setText("Total: ");
        total_price.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(total_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 160, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Your Shopping Cart");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 610, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        orderCart();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        jPanel2.setBackground(Color.lightGray);
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        jPanel2.setBackground(Color.WHITE);
    }//GEN-LAST:event_jPanel2MouseExited

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        jPanel2.setBackground(Utility.darkermiku);
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseReleased
        jPanel2.setBackground(Color.WHITE);
    }//GEN-LAST:event_jPanel2MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable cart_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel total_price;
    // End of variables declaration//GEN-END:variables
}
