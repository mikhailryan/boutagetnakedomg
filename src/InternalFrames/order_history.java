/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import config.Utility;
import config.db_connector;
import javax.swing.BoxLayout;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import config.CustomComboBoxUI;
import config.CustomScrollBarUI;
import config.OrderReceiptGenerator;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


/**
 *
 * @author Mercy
 */
public class order_history extends javax.swing.JInternalFrame {
    
    private JDateChooser date_filter;

    /**
     * Creates new form order_history
     */
    public order_history() {
        initComponents();

        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("yyyy-MM-dd");
        DatePicker datePicker = new DatePicker(settings);

        // Hide the DatePicker text field but add it to the panel (invisible)
        datePicker.getComponentDateTextField().setVisible(false);
        jPanel2.add(datePicker);  // add to a visible container (replace yourPanel with actual container)
        populateSellerComboBox();
        
        dateLabel.setText("Pick a date");
        dateLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                datePicker.openPopup();
            }
        });

        datePicker.addDateChangeListener(event -> {
            if (event.getNewDate() != null) {
                String selectedDate = event.getNewDate().toString();
                dateLabel.setText(selectedDate);
                display_orders(reseller_filter.getSelectedItem().toString(), selectedDate);
            } else {
                dateLabel.setText("Pick a date");
                display_orders(reseller_filter.getSelectedItem().toString(), "");
            }
        });
        
        reseller_filter.addActionListener(e -> {
            String selectedReseller = reseller_filter.getSelectedItem().toString();
            String selectedDate = dateLabel.getText().equals("Pick a date") ? "" : dateLabel.getText();
            display_orders(selectedReseller, selectedDate);
        });

        

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);

        display_orders(reseller_filter.getSelectedItem().toString(), "");
    }
    
    public List<Map<String, Object>> fetchOrderItems(int orderId) {
        List<Map<String, Object>> items = new ArrayList<>();
        try {
            db_connector dbcon = new db_connector();
            String query = "SELECT p.name, oi.quantity, oi.price, (oi.quantity * oi.price) AS subtotal " +
                           "FROM order_items oi " +
                           "JOIN products p ON oi.product_id = p.id " +
                           "WHERE oi.order_id = " + orderId;
            ResultSet rs = dbcon.getData(query);

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", rs.getString("name"));
                item.put("qty", rs.getInt("quantity"));
                item.put("price", rs.getDouble("price"));
                item.put("subtotal", rs.getDouble("subtotal"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public double calculateTotal(List<Map<String, Object>> items) {
        return items.stream().mapToDouble(i -> {
            Object val = i.get("subtotal");
            if (val instanceof Number) return ((Number) val).doubleValue();
            try { return Double.parseDouble(val.toString()); } 
            catch (Exception e) { return 0.0; }
        }).sum();
    }


    private void populateSellerComboBox() {
        reseller_filter.removeAllItems();
        reseller_filter.addItem("All");

        try {
            db_connector dbcon = new db_connector();
            // Select distinct resellers from orders linked to user table
            ResultSet rs = dbcon.getData("SELECT DISTINCT u.name FROM user u JOIN orders o ON u.id = o.reseller_id");

            while (rs.next()) {
                reseller_filter.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error loading resellers: " + e.getMessage());
        }
    }

    
    private void display_orders(String resellerFilter, String dateFilter) {
        try {
            db_connector dbcon = new db_connector();
            String sql = "SELECT o.id AS order_id, o.total_amount, o.order_date, u.name AS reseller_name " +
                         "FROM orders o JOIN user u ON o.reseller_id = u.id ";

            boolean hasReseller = resellerFilter != null && !resellerFilter.equals("All");
            boolean hasDate = dateFilter != null && !dateFilter.trim().isEmpty();

            if (hasReseller || hasDate) {
                sql += "WHERE ";
                if (hasReseller) {
                    sql += "u.name = '" + resellerFilter + "' ";
                    if (hasDate) sql += "AND ";
                }
                if (hasDate) {
                    sql += "DATE(o.order_date) = '" + dateFilter + "' ";
                }
            }

            sql += "ORDER BY o.order_date DESC";
            ResultSet result = dbcon.getData(sql);

            DefaultTableModel model = new DefaultTableModel(new Object[]{"Order Info", "Action", "orderId"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 1;
                }
            };

            boolean has_results = false;

            while (result.next()) {
                has_results = true;
                int orderId = result.getInt("order_id");
                String info = "Order ID: " + orderId +
                              System.lineSeparator() + "Reseller: " + result.getString("reseller_name") +
                              System.lineSeparator() + "Date: " + result.getString("order_date") +
                              System.lineSeparator() + "Total: ₱" + String.format("%.2f", result.getDouble("total_amount"));

                model.addRow(new Object[]{info, "View", orderId});
            }

            if (!has_results) {
                model.addRow(new Object[]{"No orders found", "", ""});
            }
            
            orders_table.setModel(model);
            
            orders_table.getColumnModel().getColumn(2).setMinWidth(0);
            orders_table.getColumnModel().getColumn(2).setMaxWidth(0);
            orders_table.getColumnModel().getColumn(2).setWidth(0);

            
            if (!has_results) {
                orders_table.getColumnModel().getColumn(1).setMinWidth(0);
                orders_table.getColumnModel().getColumn(1).setMaxWidth(0);
                orders_table.getColumnModel().getColumn(1).setWidth(0);
            } else {
                orders_table.getColumnModel().getColumn(1).setMinWidth(80);
                orders_table.getColumnModel().getColumn(1).setMaxWidth(100);
                orders_table.getColumnModel().getColumn(1).setPreferredWidth(90);
            }

            styleOrderTable();

        } catch (SQLException e) {
            System.out.println("Can't Load Orders: " + e.getMessage());
        }
        
        JScrollBar verticalBar = jScrollPane1.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBarUI());
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {{
            setBackground(Utility.blackish);
        }});
    }
    
    private void styleOrderTable() {
        orders_table.setRowHeight(100);
        orders_table.setShowGrid(false);
        orders_table.setTableHeader(null);
        orders_table.setBackground(Utility.blackish);
        orders_table.setIntercellSpacing(new Dimension(0, 10));

        orders_table.getColumnModel().getColumn(0).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Utility.blackish);
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Utility.darkermiku, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            if (value != null) {
                String[] lines = value.toString().split(System.lineSeparator());
                JLabel id = new JLabel(lines.length > 0 ? lines[0] : "Unknown");
                id.setFont(new Font("Segoe UI", Font.BOLD, 14));
                id.setForeground(Color.WHITE);

                JLabel reseller = new JLabel(lines.length > 1 ? lines[1] : "Unknown");
                reseller.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                reseller.setForeground(Color.LIGHT_GRAY);

                JLabel date = new JLabel(lines.length > 2 ? lines[2] : "Unknown");
                date.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                date.setForeground(Color.GRAY);

                JLabel total = new JLabel(lines.length > 3 ? lines[3] : "₱0.00");
                total.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                total.setForeground(new Color(0, 255, 180));

                panel.add(id);
                panel.add(reseller);
                panel.add(date);
                panel.add(total);
            }

            return panel;
        });

        orders_table.getColumnModel().getColumn(1).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            String info = table.getValueAt(row, 0).toString();
            if (info.equalsIgnoreCase("No orders found")) return new JLabel();

            JButton button = new JButton("View");
            button.setBackground(Utility.miku);
            button.setForeground(Color.BLACK);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        });

        orders_table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final JButton button = new JButton("View");

            {
                button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                button.setBackground(Utility.miku);
                button.setForeground(Color.BLACK);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.addActionListener(e -> {
                    int row = orders_table.getSelectedRow();
                    String info = orders_table.getValueAt(row, 0).toString();

                    if (info.equalsIgnoreCase("No orders found")) {
                        fireEditingStopped();
                        return;
                    }

                    int orderId = (int) orders_table.getValueAt(row, 2);
                    System.out.println("Order ID clicked: " + orderId);
                    
                    String userName = orders_table.getValueAt(row, 0).toString().split(System.lineSeparator())[1].replace("Reseller: ", "");
                    String filePath = "OrderSlips/OrderReceipt_" + orderId + ".pdf";
                    File file = new File(filePath);

                    if (!file.exists()) {
                        List<Map<String, Object>> items = fetchOrderItems(orderId);
                        double total = calculateTotal(items);
                        OrderReceiptGenerator.generateReceipt(orderId, userName, items, total);
                    }

                    try {
                        Desktop.getDesktop().open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                    fireEditingStopped();
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return button;
            }
        });
    }


//                    order_details_view view = new order_details_view((Frame) SwingUtilities.getWindowAncestor(orders_table), true);
//                    view.setOrderId(orderId);
//                    view.loadDetails();
//                    view.setLocationRelativeTo(orders_table);
//                    view.setVisible(true);

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
        orders_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        reseller_filter = new javax.swing.JComboBox<>();
        reseller_filter.setUI(new CustomComboBoxUI());
        jPanel3 = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 380));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orders_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(orders_table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 610, 390));

        jPanel2.setBackground(new java.awt.Color(134, 206, 203));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 30, 30));

        reseller_filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(reseller_filter, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 140, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText("Pick a date");
        jPanel3.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 140, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable orders_table;
    private javax.swing.JComboBox<String> reseller_filter;
    // End of variables declaration//GEN-END:variables
}
