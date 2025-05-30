package InternalFrames;

import Dialogs.CustomMessageDialog;
import Dialogs.CustomYesNoDialog;
import Dialogs.edit_user_form;
import Dialogs.add_user_form;
import config.CustomScrollBarUI;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
        setLabels();
        styleTable();
        unFocus();
        
        setBorder(search_bar);
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Because why tf not"> 
    private void setLabels() {
        JLabel[] labels = {search_pic, filter_pic};
        String[] paths = {"search.png", "filter.png"};
        SwingUtilities.invokeLater(() -> {
            setIcons(labels, paths);
        });
        
    }
    
    private void setBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 38, 0, 0) 
        ));
    }
    
    public void refreshData(){
        display_data();
    }
    
    private void display_data(){
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.getData("SELECT id AS 'ID', name AS 'Full Name', username AS 'Username', email AS 'Email Address', role AS 'Role', status AS 'Status' FROM user WHERE role != 'Admin' ORDER BY status");
            
            int rowCount = 0;

            while (result.next()) {
                rowCount++;
            }
            result.beforeFirst();
            users_table.setModel(DbUtils.resultSetToTableModel(result));
            
            users_count.setText("All Users: " + rowCount);
            
            users_table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            users_table.getColumnModel().getColumn(1).setPreferredWidth(120); // Full Name
            users_table.getColumnModel().getColumn(2).setPreferredWidth(150); // Username
            users_table.getColumnModel().getColumn(3).setPreferredWidth(250); // Email Address
            users_table.getColumnModel().getColumn(4).setPreferredWidth(80); // Role
            users_table.getColumnModel().getColumn(5).setPreferredWidth(80); // Account Status
            
            users_table.getTableHeader().setBackground(new Color(19,122,127));
            
            String[] statuses = {"Pending", "Inactive", "Active"};
            JComboBox<String> statusComboBox = new JComboBox<>(statuses);
            TableColumn statusColumn = users_table.getColumnModel().getColumn(5);
            DefaultCellEditor editor = new DefaultCellEditor(statusComboBox);
            statusColumn.setCellEditor(editor);
            
            statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<String> comboBox = (JComboBox<String>) super.getTableCellEditorComponent(table, value, isSelected, row, column);

                    if (value != null) {
                        comboBox.setSelectedItem(value);
                    }

                    SwingUtilities.invokeLater(() -> {
                        if (comboBox.isShowing()) {
                            comboBox.showPopup();
                        }
                    });

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

                    String new_status = users_table.getValueAt(row, 5).toString(); 
                    String id = users_table.getValueAt(row, 0).toString();

                    db_connector.updateDatabase("UPDATE user SET status = '"+ new_status +"' WHERE id = '"+ id +"'");

                }

                @Override
                public void editingCanceled(ChangeEvent ce) {}
            });
        } catch (SQLException e) {
            System.out.println("Can't Connect to Database: " + e.getMessage());
        } 
        
        JScrollBar verticalBar = jScrollPane1.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBarUI());
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel() {{
            setBackground(Utility.blackish);
        }});
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

    private void setIcons(JLabel[] labels, String[] paths) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setIcon(resizeImage("/images/" + paths[i], labels[i]));
        }
    }
    
    private ImageIcon resizeImage(String path, JLabel label) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(label.getWidth() - 10, label.getHeight() - 10, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    
    private void unFocus(){
        setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getInitialComponent(Window window) {
                return null; 
            }

            @Override
            public Component getFirstComponent(Container focusCycleRoot) { return null; }
            @Override
            public Component getLastComponent(Container focusCycleRoot) { return null; }
            @Override
            public Component getDefaultComponent(Container focusCycleRoot) { return null; }
            @Override
            public Component getComponentAfter(Container focusCycleRoot, Component aComponent) { return null; }
            @Override
            public Component getComponentBefore(Container focusCycleRoot, Component aComponent) { return null; }
        });
    }// </editor-fold>  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        users_table = new javax.swing.JTable();
        delete_button = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        users_count = new javax.swing.JLabel();
        search_pic = new javax.swing.JLabel();
        search_bar = new javax.swing.JTextField();
        add_user_btn = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        filter_btn = new javax.swing.JPanel();
        filter_pic = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edit_button = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(610, 500));
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

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 380));
        jPanel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        users_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        users_table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        users_table.getTableHeader().setResizingAllowed(false);
        users_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(users_table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, 380));

        delete_button.setBackground(Utility.miku);
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
        delete_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Delete");
        delete_button.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel1.add(delete_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 90, 30));

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 27)); // NOI18N
        jLabel2.setText("User Management");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 330, 40));

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        jLabel3.setText("View, manage, and update user accounts");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 290, 30));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        users_count.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        users_count.setText("All Users: ");
        jPanel2.add(users_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 30));
        jPanel2.add(search_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 30, 30));

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
        jPanel2.add(search_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 200, 30));

        add_user_btn.setBackground(new java.awt.Color(55, 59, 62));
        add_user_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_user_btnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_user_btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_user_btnMouseExited(evt);
            }
        });
        add_user_btn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Add user");
        add_user_btn.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 30));

        jPanel2.add(add_user_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 90, 30));

        filter_btn.setBackground(new java.awt.Color(255, 255, 255));
        filter_btn.setBorder(javax.swing.BorderFactory.createLineBorder(Utility.darkermiku));
        filter_btn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        filter_btn.add(filter_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Filter");
        filter_btn.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 60, 30));

        jPanel2.add(filter_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 80, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 610, 50));

        edit_button.setBackground(Utility.miku);
        edit_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                edit_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                edit_buttonMouseExited(evt);
            }
        });
        edit_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Edit");
        edit_button.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel1.add(edit_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        db_connector conn = new db_connector();
        
        if (row == -1) {
            CustomMessageDialog.showMessage(null, "Please select a row to delete.", "No Selection");
            return;
        }

        boolean confirm = CustomYesNoDialog.showConfirm(null, "Are you sure you want to delete this user?", "Confirm Delete");
        if (!confirm) {
            return;
        }

        String id = users_table.getValueAt(row, 0).toString(); 

        if (db_connector.updateDatabase("DELETE FROM user WHERE id = '" + id + "'")) {
            display_data();
            conn.insertLog(Session.getInstance().getUserId(), "Deleted user with ID:" + id);
            CustomMessageDialog.showMessage(null, "User deleted successfully.", "Deleted");
        } else {
            CustomMessageDialog.showMessage(null, "Failed to delete user.", "Error");
        }
    }//GEN-LAST:event_delete_buttonMouseClicked

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

    private void add_user_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_user_btnMouseClicked
        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            add_user_form.addUserDialog(desktopPane);
        }
    }//GEN-LAST:event_add_user_btnMouseClicked

    private void add_user_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_user_btnMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_add_user_btnMouseEntered

    private void add_user_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_user_btnMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_add_user_btnMouseExited
            
    private void edit_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_buttonMouseClicked
        int row = users_table.getSelectedRow(); 

        if (row == -1) {
            CustomMessageDialog.showMessage(null, "Please select a row to edit.", "No Selection");
            return;
        }

        String id = users_table.getValueAt(row, 0).toString();
        
        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            edit_user_form.editUserDialog(desktopPane, id);
        }
    }//GEN-LAST:event_edit_buttonMouseClicked

    private void edit_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_buttonMouseEntered
        edit_button.setBackground(new Color(19,122,127));
    }//GEN-LAST:event_edit_buttonMouseEntered

    private void edit_buttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_buttonMouseExited
        edit_button.setBackground(new Color(134,206,203));
    }//GEN-LAST:event_edit_buttonMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add_user_btn;
    private javax.swing.JPanel delete_button;
    private javax.swing.JPanel edit_button;
    private javax.swing.JPanel filter_btn;
    private javax.swing.JLabel filter_pic;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField search_bar;
    private javax.swing.JLabel search_pic;
    private javax.swing.JLabel users_count;
    private javax.swing.JTable users_table;
    // End of variables declaration//GEN-END:variables
}
