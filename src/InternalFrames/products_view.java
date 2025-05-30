/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import Dialogs.CustomMessageDialog;
import Dialogs.CustomYesNoDialog;
import Dialogs.add_product;
import Dialogs.verify_email;
import config.CustomScrollBarUI;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Mercy
 */
public class products_view extends javax.swing.JInternalFrame {
    

    /**
     * Creates new form products_view
     */
    public products_view() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        
        JLabel[] labels = {product_pic};
        String[] paths = {"box.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
        
        bi.setNorthPane(null);
        
        display_products();
        styleProductTable();   
    }
    
    private void display_products() {
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.getData(
                "SELECT id AS 'ID', name AS 'Product Name', price AS 'Price', stock AS 'Stock' " +
                "FROM products WHERE seller_id = '" + Session.getInstance().getUserId() + "'"
            );

            products_table.setModel(DbUtils.resultSetToTableModel(result));

            products_table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
            products_table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Product Name
            products_table.getColumnModel().getColumn(2).setPreferredWidth(90);   // Price
            products_table.getColumnModel().getColumn(3).setPreferredWidth(90);   // Stock

            products_table.getTableHeader().setBackground(new Color(19,122,127));

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
        products_table.setRowHeight(27);
        products_table.setShowGrid(false);
        products_table.setGridColor(Color.LIGHT_GRAY);
        products_table.getTableHeader().setBackground(Color.BLACK);
        products_table.getTableHeader().setForeground(Color.WHITE);
        products_table.setBackground(Utility.blackish);
        products_table.setForeground(Color.BLACK);

        products_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    label.setBackground(row % 2 == 0 ? Color.GRAY : Utility.grayish);
                } else {
                    label.setBackground(Utility.darkermiku);
                }

                return label;
            }
        });

        JTableHeader header = products_table.getTableHeader();
        header.setPreferredSize(new Dimension(products_table.getWidth(), 30));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString(), JLabel.LEADING);
                label.setOpaque(true);
                label.setBackground(Utility.blackish);
                label.setForeground(Color.WHITE);
                return label;
            }
        });
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
        jPanel2 = new javax.swing.JPanel();
        product_pic = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        restock_button = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

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

        jPanel1.add(productScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 610, 380));

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(product_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 30, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Add");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 60, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 90, 30));

        restock_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restock_buttonMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Restock");

        javax.swing.GroupLayout restock_buttonLayout = new javax.swing.GroupLayout(restock_button);
        restock_button.setLayout(restock_buttonLayout);
        restock_buttonLayout.setHorizontalGroup(
            restock_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(restock_buttonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );
        restock_buttonLayout.setVerticalGroup(
            restock_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(restock_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        
        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            add_product.addProductDialog(desktopPane);
        }
    }//GEN-LAST:event_jPanel2MouseClicked
    db_connector conn = new db_connector();
    private void restock_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restock_buttonMouseClicked
        int selectedRow = products_table.getSelectedRow();

        if (selectedRow != -1) {  // Make sure a row is selected
            int productId = (int) products_table.getValueAt(selectedRow, 0); 

            int currentQuantity = (int) products_table.getValueAt(selectedRow, 3); 

            String input = JOptionPane.showInputDialog(null, "Enter the number of units to restock:");

            try {
                int restockAmount = Integer.parseInt(input);

                int updatedQuantity = currentQuantity + restockAmount;

                products_table.setValueAt(updatedQuantity, selectedRow, 3);

                String sql = "UPDATE products SET stock = ? WHERE id = ?";
                
                Connection connn = conn.getConnection();
                PreparedStatement pstmt = connn.prepareStatement(sql);
              
                pstmt.setInt(1, updatedQuantity);  // Set the updated stock
                pstmt.setInt(2, productId);        // Set the product ID

                // Call the updateDatabase method to execute the update query
                boolean success = db_connector.updateDatabase(pstmt);

                if (success) {
                    // Optional: Notify user of successful restock
                    JOptionPane.showMessageDialog(null, "Restocked " + restockAmount + " units and updated the database!");
                } else {
                    // Handle failure (couldn't update the database)
                    JOptionPane.showMessageDialog(null, "Failed to update the database.");
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (non-numeric or empty input)
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            } catch (SQLException ex) {
                Logger.getLogger(products_view.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // If no row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Please select a product to restock.");
        }

    }//GEN-LAST:event_restock_buttonMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        int selected_row = products_table.getSelectedRow();
        
        if (selected_row == -1) {
            CustomMessageDialog.showMessage(null, "Please select a row to delete.", "No Selection");
            return;
        }

        boolean confirm = CustomYesNoDialog.showConfirm(null, "Are you sure you want to remove this Product?", "Confirm Delete");
        if (!confirm) {
            return;
        }

        String id = products_table.getValueAt(selected_row, 0).toString(); 

        if (db_connector.updateDatabase("DELETE FROM products WHERE id = '" + id + "'")) {
            
            display_products();
            styleProductTable();   
            conn.insertLog(Session.getInstance().getUserId(), "Deleted Product with ID:" + id);
            CustomMessageDialog.showMessage(null, "Product deleted successfully.", "Deleted");
        } else {
            CustomMessageDialog.showMessage(null, "Failed to delete Product.", "Error");
        }
    }//GEN-LAST:event_jPanel3MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane productScrollPane;
    private javax.swing.JLabel product_pic;
    private javax.swing.JTable products_table;
    private javax.swing.JPanel restock_button;
    // End of variables declaration//GEN-END:variables
}
