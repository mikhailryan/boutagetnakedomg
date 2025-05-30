/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theShop;

import config.CustomScrollBarUI;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Mercy
 */
public class Logs extends javax.swing.JPanel {

    /**
     * Creates new form Logs
     */
    public Logs() {
        initComponents();
        populateUserFilter();
        display_logs("All", "");
        styleLogsTable();
    }
    
    private void display_logs(String userFilter, String actionKeyword) {
        try {   
            db_connector dbcon = new db_connector();
            Connection conn = dbcon.getConnection();

            StringBuilder sql = new StringBuilder(
                "SELECT user.name AS 'User', logs.action AS 'Action', logs.timestamp AS 'Time' " +
                "FROM logs " +
                "JOIN user ON logs.user_id = user.id " +
                "WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            if (userFilter != null && !userFilter.equals("All")) {
                sql.append(" AND user.name = ?");
                params.add(userFilter);
            }

            if (actionKeyword != null && !actionKeyword.trim().isEmpty()) {
                sql.append(" AND logs.action LIKE ?");
                params.add("%" + actionKeyword + "%");
            }

            sql.append(" ORDER BY logs.timestamp DESC");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet result = stmt.executeQuery();
            logs_table.setModel(DbUtils.resultSetToTableModel(result));

            logs_table.getColumnModel().getColumn(0).setPreferredWidth(150);  // User
            logs_table.getColumnModel().getColumn(1).setPreferredWidth(400);  // Action
            logs_table.getColumnModel().getColumn(2).setPreferredWidth(180);  // Time
            logs_table.getTableHeader().setBackground(new Color(19, 122, 127));

            JScrollBar verticalBar = jScrollPane1.getVerticalScrollBar();
            verticalBar.setUI(new CustomScrollBarUI());
            jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {{
                setBackground(Utility.blackish);
            }});

        } catch (SQLException e) {
            System.out.println("Can't Load Logs: " + e.getMessage());
        }
    }

    private void styleLogsTable() {
        logs_table.setRowHeight(27);
        logs_table.setShowGrid(false);
        logs_table.setGridColor(Color.LIGHT_GRAY);
        logs_table.getTableHeader().setBackground(Color.BLACK);
        logs_table.getTableHeader().setForeground(Color.WHITE);
        logs_table.setBackground(Utility.blackish);
        logs_table.setForeground(Color.BLACK);

        logs_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JTableHeader header = logs_table.getTableHeader();
        header.setPreferredSize(new Dimension(logs_table.getWidth(), 30));
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


    
    private void populateUserFilter() {
        user_filter.removeAllItems();
        user_filter.addItem("All");
        try {
            db_connector conn = new db_connector();
            ResultSet rs = conn.getData("SELECT DISTINCT name FROM user");
            while (rs.next()) {
                user_filter.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        logs_table = new javax.swing.JTable();
        user_filter = new javax.swing.JComboBox<>();
        action_search = new javax.swing.JTextField();

        setBackground(new java.awt.Color(134, 206, 203));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logs_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(logs_table);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 82, 450, 580));

        user_filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(user_filter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 120, 30));
        add(action_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 200, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField action_search;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable logs_table;
    private javax.swing.JComboBox<String> user_filter;
    // End of variables declaration//GEN-END:variables
}
