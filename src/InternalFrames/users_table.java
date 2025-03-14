package InternalFrames;

import config.Utility;
import config.db_connector;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

public class users_table extends javax.swing.JInternalFrame {

    /**
     * Creates new form users_table
     */
    public users_table() {
        initComponents();   
        display_data();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
        styleTable();
    }
    
    private void display_data(){
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.getData("SELECT id AS 'ID', name AS 'Full Name', username AS 'Username', email AS 'Email Address', status AS 'Account Status' FROM user WHERE role = 'user'");
            users_table.setModel(DbUtils.resultSetToTableModel(result));
            
            users_table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            users_table.getColumnModel().getColumn(1).setPreferredWidth(120); // Full Name
            users_table.getColumnModel().getColumn(2).setPreferredWidth(120); // Username
            users_table.getColumnModel().getColumn(3).setPreferredWidth(200); // Email Address
            users_table.getColumnModel().getColumn(4).setPreferredWidth(100); // Account Status
            
            users_table.getTableHeader().setBackground(new Color(19,122,127));
            
            String[] statuses = {"pending", "inactive", "active"};
            JComboBox<String> statusComboBox = new JComboBox<>(statuses);
            TableColumn statusColumn = users_table.getColumnModel().getColumn(4);
            DefaultCellEditor editor = new DefaultCellEditor(statusComboBox);
            statusColumn.setCellEditor(editor);
            
            statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<String> comboBox = (JComboBox<String>) super.getTableCellEditorComponent(table, value, isSelected, row, column);

                    if (value != null) {
                        comboBox.setSelectedItem(value);
                    }

                    SwingUtilities.invokeLater(() -> comboBox.showPopup());

                    return comboBox;
                }
            });
            
            editor.addCellEditorListener(new CellEditorListener() {  
                @Override
                public void editingStopped(ChangeEvent e) {
                    int row = users_table.getSelectedRow(); 

                    if (row == -1) {
                        return;
                    }

                    String new_status = users_table.getValueAt(row, 4).toString(); 
                    String id = users_table.getValueAt(row, 0).toString();

                    db_connector.updateDatabase("UPDATE user SET status = '"+ new_status +"' WHERE id = '"+ id +"'");

                }

                @Override
                public void editingCanceled(ChangeEvent ce) {
                    
                }

            });
        } catch (SQLException e) {
            System.out.println("Can't Connect to Database: " + e.getMessage());
        } 
    }
    
    private void styleTable() {
        users_table.setRowHeight(27);
        users_table.setShowGrid(false);
        users_table.setGridColor(Color.LIGHT_GRAY); 
        users_table.getTableHeader().setBackground(Color.BLACK); 
        users_table.getTableHeader().setForeground(Color.WHITE); 
        users_table.setBackground(Utility.blackish);
        users_table.setForeground(Color.BLACK);

        users_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    label.setBackground(row % 2 == 0 ? Color.GRAY : Utility.grayish);
                }else {
                    label.setBackground(Utility.darkermiku);
                }

                return label;
            }
        });
        
        JTableHeader header = users_table.getTableHeader();
        header.setPreferredSize(new Dimension(users_table.getWidth(), 30));
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
        users_table = new javax.swing.JTable();
        delete_button = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(610, 380));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 380));
        jPanel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.setLayout(null);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(610, 380));

        users_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(users_table);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 610, 340);

        delete_button.setBackground(new java.awt.Color(134, 206, 203));
        delete_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                delete_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                delete_buttonMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Delete");

        javax.swing.GroupLayout delete_buttonLayout = new javax.swing.GroupLayout(delete_button);
        delete_button.setLayout(delete_buttonLayout);
        delete_buttonLayout.setHorizontalGroup(
            delete_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        delete_buttonLayout.setVerticalGroup(
            delete_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(delete_button);
        delete_button.setBounds(480, 340, 100, 30);

        jPanel3.setBackground(new java.awt.Color(134, 206, 203));

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

        jPanel1.add(jPanel3);
        jPanel3.setBounds(350, 340, 100, 30);

        jPanel4.setBackground(new java.awt.Color(134, 206, 203));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4);
        jPanel4.setBounds(220, 340, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("R"), "refreshAction");

        actionMap.put("refreshAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display_data();
            }

        });
    }//GEN-LAST:event_formInternalFrameOpened

    private void delete_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_buttonMouseEntered
        delete_button.setBackground(new Color(19,122,127));
    }//GEN-LAST:event_delete_buttonMouseEntered

    private void delete_buttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_buttonMouseExited
        delete_button.setBackground(new Color(134,206,203));
    }//GEN-LAST:event_delete_buttonMouseExited

    private void delete_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_buttonMouseClicked
        int row = users_table.getSelectedRow(); 

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String id = users_table.getValueAt(row, 0).toString(); 

        if (db_connector.updateDatabase("DELETE FROM user WHERE id = '" + id + "'")) {
            display_data();
            JOptionPane.showMessageDialog(this, "User deleted successfully.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_delete_buttonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel delete_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable users_table;
    // End of variables declaration//GEN-END:variables
}
