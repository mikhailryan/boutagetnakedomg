/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import config.CustomComboBoxUI;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Point;
import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author SCC-COLLEGE
 */
public class edit_user_form extends javax.swing.JDialog {
    private static edit_user_form instance;  
    private final db_connector conn = new db_connector();
    static String userId = "0";
    
    String name = "";
    String email = "";
    String username = "";
    String role = "";
    String status = "";
    
    /**
     * Creates new form edit_user_form
     * @param parent
     * @param modal
     * @param id
     */
    public edit_user_form(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        getData(this.userId);
        
        setLabels();
        Utility.setBorders(name_input, username_input, email_input);
        
        unFocus();
    }
    
    private void getData(String id){
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Error: Invalid user ID.");
            return;
        }
        
        String sql = "SELECT * FROM user WHERE id = " + id;
        try {
            ResultSet result = conn.getData(sql);
            
            if (result.next()) {
                this.name = result.getString("name");
                this.email = result.getString("email");
                this.username = result.getString("username");
                this.status = result.getString("status");
                this.role = result.getString("role");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());        
        }
        
        name_input.setText(this.name);
        email_input.setText(this.email);
        username_input.setText(this.username);
        roles_selection.setSelectedItem(this.role);
        status_selection.setSelectedItem(this.status);
    }
    
    private void setLabels() {
        JLabel[] labels = {minimize_button, jLabel11};
        String[] paths = {"minimize-sign.png", "user.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
        
    } 
    
    private void setInvalidBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.RED, 2), 
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }

    private void resetBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }
    
    private void displayError(JLabel field, String message) {
        field.setText(message);
    }
    
    public static void editUserDialog(JDesktopPane desktopPane, String id) {
        userId = id;
        if (instance == null) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(desktopPane);
            if (parentFrame != null) {
                instance = new edit_user_form(parentFrame, true);
                centerDialog(desktopPane); 
            } else {
                System.err.println("Error: Parent frame not found!");
                return;
            }
        }

        if (!instance.isVisible()) {
            centerDialog(desktopPane);
            instance.setVisible(true);
        }
        
        
    }

    private static void centerDialog(JDesktopPane desktopPane) {
        Point desktopLocation = desktopPane.getLocationOnScreen();
        int x = desktopLocation.x + (desktopPane.getWidth() - instance.getWidth()) / 2;
        int y = desktopLocation.y + (desktopPane.getHeight() - instance.getHeight()) / 2;
        instance.setLocation(x, y);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cancel_button = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        add_button = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        name_input = new javax.swing.JTextField();
        username_input = new javax.swing.JTextField();
        email_input = new javax.swing.JTextField();
        roles_selection = new javax.swing.JComboBox<>();
        roles_selection.setUI(new CustomComboBoxUI());
        jLabel6 = new javax.swing.JLabel();
        status_error = new javax.swing.JLabel();
        username_error = new javax.swing.JLabel();
        email_error = new javax.swing.JLabel();
        name_error = new javax.swing.JLabel();
        password_error = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        status_selection = new javax.swing.JComboBox<>();
        status_selection.setUI(new CustomComboBoxUI());
        role_error = new javax.swing.JLabel();
        minimize_button = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(Utility.miku);
        jPanel1.setBorder(new javax.swing.border.LineBorder(Utility.blackish, 3, true));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 30)); // NOI18N
        jLabel1.setText("Edit User");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 30, 280, 30);

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        jLabel2.setText("Modify User Details");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 60, 280, 20);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(Utility.darkermiku);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Edit User");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 134, 50));
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel4.setText("Name");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 70, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel5.setText("Username");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 70, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel8.setText("Status");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 70, 30));

        cancel_button.setBackground(new java.awt.Color(255, 255, 255));
        cancel_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancel_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancel_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancel_buttonMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cancel");

        javax.swing.GroupLayout cancel_buttonLayout = new javax.swing.GroupLayout(cancel_button);
        cancel_button.setLayout(cancel_buttonLayout);
        cancel_buttonLayout.setHorizontalGroup(
            cancel_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancel_buttonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addContainerGap())
        );
        cancel_buttonLayout.setVerticalGroup(
            cancel_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel3.add(cancel_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 80, 30));

        add_button.setBackground(Utility.blackish);
        add_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_buttonMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("OK");

        javax.swing.GroupLayout add_buttonLayout = new javax.swing.GroupLayout(add_button);
        add_button.setLayout(add_buttonLayout);
        add_buttonLayout.setHorizontalGroup(
            add_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_buttonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addContainerGap())
        );
        add_buttonLayout.setVerticalGroup(
            add_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel3.add(add_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 80, 30));

        name_input.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        name_input.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                name_inputCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        name_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                name_inputKeyTyped(evt);
            }
        });
        jPanel3.add(name_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 180, 30));

        username_input.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        username_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                username_inputKeyTyped(evt);
            }
        });
        jPanel3.add(username_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 180, 30));

        email_input.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        email_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                email_inputKeyTyped(evt);
            }
        });
        jPanel3.add(email_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 180, 30));

        roles_selection.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        roles_selection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select role", "Admin", "User" }));
        roles_selection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                roles_selectionItemStateChanged(evt);
            }
        });
        jPanel3.add(roles_selection, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 180, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel6.setText("Email");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 70, 30));

        status_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        status_error.setForeground(new java.awt.Color(255, 0, 0));
        status_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(status_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 150, 10));

        username_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        username_error.setForeground(new java.awt.Color(255, 0, 0));
        username_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(username_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 150, 10));

        email_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        email_error.setForeground(new java.awt.Color(255, 0, 0));
        email_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(email_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 150, 10));

        name_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        name_error.setForeground(new java.awt.Color(255, 0, 0));
        name_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(name_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 150, 10));

        password_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        password_error.setForeground(new java.awt.Color(255, 0, 0));
        password_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(password_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 150, 10));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        jLabel12.setText("Role");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 70, 30));

        status_selection.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        status_selection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select status", "Pending", "Active" }));
        status_selection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                status_selectionItemStateChanged(evt);
            }
        });
        jPanel3.add(status_selection, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 180, 30));

        role_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        role_error.setForeground(new java.awt.Color(255, 0, 0));
        role_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(role_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 150, 10));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(60, 110, 320, 360);

        minimize_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        minimize_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize_buttonMouseClicked(evt);
            }
        });
        jPanel1.add(minimize_button);
        minimize_button.setBounds(410, 0, 30, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancel_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel_buttonMouseClicked
        this.dispose();
        instance = null;
    }//GEN-LAST:event_cancel_buttonMouseClicked

    private void cancel_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel_buttonMouseEntered
        cancel_button.setBackground(new Color(204,0,0));
        jLabel9.setForeground(Color.WHITE);
    }//GEN-LAST:event_cancel_buttonMouseEntered

    private void cancel_buttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancel_buttonMouseExited
        cancel_button.setBackground(Color.WHITE);
        jLabel9.setForeground(Color.BLACK);
    }//GEN-LAST:event_cancel_buttonMouseExited

    private void add_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_buttonMouseClicked
  
        boolean valid_to_add = true;

        String name = name_input.getText().trim();
        String username = username_input.getText().trim();
        String email = email_input.getText().trim();
        String role = (String)roles_selection.getSelectedItem();
        String status = (String)status_selection.getSelectedItem();
        
        if(name.isEmpty() || name.equals("Enter full name...")) {
            setInvalidBorder(name_input);
            displayError(name_error, "Field Required!");
            valid_to_add = false;
        }
        if(username.isEmpty() || username.equals("Enter username...")) {
            setInvalidBorder(username_input);
            displayError(username_error, "Field Required!");
            valid_to_add = false;
        }
        if(email.isEmpty() || email.equals("Enter email...")) {
            setInvalidBorder(email_input);
            displayError(email_error, "Field Required!");
            valid_to_add = false;
        } else if(!Utility.isValidEmail(email)) {
            setInvalidBorder(email_input);
            displayError(email_error, "Invalid Email!");
            valid_to_add = false;
        }
        if(role.equals("Select role")){
            roles_selection.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            displayError(role_error, "Field Required!");
            valid_to_add = false;
        }
        if(status.equals("Select status")){
            status_selection.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            displayError(status_error, "Field Required!");
            valid_to_add = false;
        }
        
        try {
            if(conn.fieldExists("username", username) && !username.equals(this.username)){
                setInvalidBorder(username_input);
                displayError(username_error, "Username Already Taken!");
                valid_to_add = false;
            }
            if(conn.fieldExists("email", email) && !email.equals(this.email)){
                setInvalidBorder(email_input);
                displayError(email_error, "Email Already Exist!");
                valid_to_add = false;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if((name.equals(this.name) && email.equals(this.email) && username.equals(this.username) && role.equals(this.role) && status.equals(this.status))) {
            this.dispose();
            instance = null;
            return;
        }
        
        if(valid_to_add) {
            db_connector.updateDatabase("UPDATE `user` SET "
                                    + "name = '"+name+"', "
                                    + "username = '"+username+"', "
                                    + "email = '"+email+"', "
                                    + "role = '"+role+"', "
                                    + "status = '"+status+"' "
                                        + "WHERE id = '"+userId+"'");

            JOptionPane.showMessageDialog(null, "User Edited Successfully!");
            new users_table().refreshData();
            this.dispose();
            instance = null;
        }
        
    }//GEN-LAST:event_add_buttonMouseClicked

    private void add_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_buttonMouseEntered
        add_button.setBackground(Utility.darkermiku);
    }//GEN-LAST:event_add_buttonMouseEntered

    private void add_buttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_buttonMouseExited
        add_button.setBackground(Utility.blackish);
    }//GEN-LAST:event_add_buttonMouseExited

    private void name_inputCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_name_inputCaretPositionChanged

    }//GEN-LAST:event_name_inputCaretPositionChanged

    private void name_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_name_inputKeyTyped
        resetBorder(name_input);
        displayError(name_error, "");
    }//GEN-LAST:event_name_inputKeyTyped

    private void username_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_username_inputKeyTyped
        resetBorder(username_input);
        displayError(username_error, "");
    }//GEN-LAST:event_username_inputKeyTyped

    private void email_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_email_inputKeyTyped
        resetBorder(email_input);
        displayError(email_error, "");
    }//GEN-LAST:event_email_inputKeyTyped

    private void roles_selectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_roles_selectionItemStateChanged
        roles_selection.setBorder(BorderFactory.createLineBorder(Utility.darkermiku, 1));
        displayError(role_error, "");
    }//GEN-LAST:event_roles_selectionItemStateChanged

    private void status_selectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_status_selectionItemStateChanged
        status_selection.setBorder(BorderFactory.createLineBorder(Utility.darkermiku, 1));
        displayError(status_error, "");
    }//GEN-LAST:event_status_selectionItemStateChanged

    private void minimize_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimize_buttonMouseClicked
        this.dispose();
        instance = null;
    }//GEN-LAST:event_minimize_buttonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add_button;
    private javax.swing.JPanel cancel_button;
    private javax.swing.JLabel email_error;
    private javax.swing.JTextField email_input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel minimize_button;
    private javax.swing.JLabel name_error;
    private javax.swing.JTextField name_input;
    private javax.swing.JLabel password_error;
    private javax.swing.JLabel role_error;
    private javax.swing.JComboBox<String> roles_selection;
    private javax.swing.JLabel status_error;
    private javax.swing.JComboBox<String> status_selection;
    private javax.swing.JLabel username_error;
    private javax.swing.JTextField username_input;
    // End of variables declaration//GEN-END:variables
}
