/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import config.CustomScrollBarUI;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Mercy
 */
public class ordered_products extends javax.swing.JInternalFrame {

    /**
     * Creates new form ordered_products
     */
    public ordered_products() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);  
        
        display_products_sold();
        styleProductTable();
    }
    
    private void display_products_sold() {
        try {
            db_connector dbcon = new db_connector();
            String query = "SELECT p.id AS 'ID', p.name AS 'Product Name', " +
                           "SUM(oi.quantity) AS 'Total Sold', " +
                           "SUM(oi.quantity * oi.price) AS 'Total Revenue' " +
                           "FROM products p " +
                           "JOIN order_items oi ON p.id = oi.product_id " +
                           "JOIN orders o ON oi.order_id = o.id " +
                           "WHERE p.seller_id = '" + Session.getInstance().getUserId() + "' " +
                           "GROUP BY p.id";

            ResultSet result = dbcon.getData(query);
            DefaultTableModel model = (DefaultTableModel) DbUtils.resultSetToTableModel(result);

            double totalRevenue = 0;
            int totalProducts = model.getRowCount();

            if (totalProducts == 0) {
                model.setRowCount(0);
//                model.setColumnIdentifiers(new String[]{"Message"});
                model.addRow(new Object[]{"No products sold yet."});
                products_table.setModel(model);
//                products_table.getTableHeader().setVisible(false);
            } else {
                for (int i = 0; i < model.getRowCount(); i++) {
                    Object value = model.getValueAt(i, 3);
                    if (value != null) {
                        totalRevenue += Double.parseDouble(value.toString());
                    }
                }

                products_table.setModel(model);
                products_table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
                products_table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Product Name
                products_table.getColumnModel().getColumn(2).setPreferredWidth(90);   // Total Sold
                products_table.getColumnModel().getColumn(3).setPreferredWidth(120);  // Total Revenue
                products_table.getTableHeader().setVisible(true);
            }

            products_table.getTableHeader().setBackground(new Color(19, 122, 127));
            
            prods.setText("Products Sold: " + totalProducts);
            total.setText("Total Revenue: " + totalRevenue);

        } catch (SQLException e) {
            System.out.println("Can't Load Sold Products: " + e.getMessage());
        }

        JScrollBar verticalBar = jScrollPane1.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBarUI());
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {{
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
        jScrollPane1 = new javax.swing.JScrollPane();
        products_table = new javax.swing.JTable();
        prods = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 470));
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
        jScrollPane1.setViewportView(products_table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 610, 340));

        prods.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        prods.setText("Products Sold:");
        jPanel1.add(prods, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 230, 30));

        total.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        total.setText("Total Revenue: ");
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 230, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Products Sold Summary");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 610, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel prods;
    private javax.swing.JTable products_table;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
