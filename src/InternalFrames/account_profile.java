/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import java.awt.*;
import javax.swing.*;
import config.*;
import Dialogs.change_pass;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author SCC-COLLEGE
 */
public class account_profile extends javax.swing.JInternalFrame {
    
    int id = Session.getInstance().getUserId();
    String name = "";
    String email = "";  
    String username = "";
    
    db_connector conn = new db_connector();
    
    /**
     * Creates new form account_profile
     */
    public account_profile() {
        initComponents();
        unFocus();
        
        getData();
        addPanelMouseListener();
        
        
        setLabels();
        Utility.setBorders(name_field, email_field, username_field);
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
    }
    
    private void getData(){
        String sql = "SELECT * FROM user WHERE id = " + id;
        try {
            ResultSet result = conn.getData(sql);
            
            if (result.next()) {
                this.name = result.getString("name");
                this.email = result.getString("email");
                this.username = result.getString("username");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());        
        }
        
        name_label.setText(this.name);
        email_label.setText(this.email);
        
        name_field.setText(this.name);
        email_field.setText(this.email);
        username_field.setText(this.username);
    }
    
    private void setLabels(){
        JLabel[] labels = {user_pic};
        String[] paths = {"user.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
    }
    
    boolean valid_to_save = true;
    private void addPanelMouseListener() {
        save_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!save_button.isEnabled()) {
                    return; 
                }
                
                String name = name_field.getText().trim();
                String email = email_field.getText().trim();
                String username = username_field.getText().trim();
                
             
                if(valid_to_save) {
                    db_connector.updateDatabase("UPDATE `user` SET "
                            + "name = '"+name+"', "
                            + "username = '"+username+"', "
                            + "email = '"+email+"' "
                                + "WHERE id = '"+id+"'");

                    JOptionPane.showMessageDialog(null, "Edited Successfully!");
                    getData();
                    disableButtons();
                }
            }
        });
        
        reset_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!reset_button.isEnabled()) {
                    return; 
                }
                
                name_field.setText(name);
                email_field.setText(email);
                username_field.setText(username);
                
                disableButtons();
                
                resetBorder(name_field);
                resetBorder(username_field);
                resetBorder(email_field);
                
                displayError(name_error, "");
                displayError(email_error, "");
                displayError(username_error, "");
                
                
            }
        });
    }
    
    private void setInvalidBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.RED, 2), 
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }
    
    private void displayError(JLabel field, String message) {
        field.setText(message);
    }
    
    public void resetBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }
    
    private void disableButtons(){
        save_button.setEnabled(false);
        save_button.setBackground(Color.WHITE);
        jLabel2.setForeground(Color.WHITE);
        
        reset_button.setEnabled(false);
        reset_button.setBackground(Color.WHITE);
        jLabel2.setForeground(Color.WHITE);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        user_pic = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        name_label = new javax.swing.JLabel();
        email_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        save_button = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        name_field = new javax.swing.JTextField();
        email_field = new javax.swing.JTextField();
        username_field = new javax.swing.JTextField();
        reset_button = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        change_pass_btn = new javax.swing.JLabel();
        email_error = new javax.swing.JLabel();
        username_error = new javax.swing.JLabel();
        name_error = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setPreferredSize(new java.awt.Dimension(410, 400));

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(410, 400));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user_pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        jPanel3.add(user_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 110, 100));

        jPanel4.setBackground(Utility.darkermiku);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 90));

        name_label.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        name_label.setText("Name");
        jPanel3.add(name_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 350, 30));

        email_label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        email_label.setText("Email");
        jPanel3.add(email_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 330, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel3.setText("Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 160, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel4.setText("Email");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 160, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel5.setText("Username");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 160, 30));

        save_button.setBackground(new java.awt.Color(255, 255, 255));
        save_button.setEnabled(false);
        save_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Save Changes");
        save_button.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel3.add(save_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 90, 30));

        name_field.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        name_field.setText("Name");
        name_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldsChanged(evt);
            }
        });
        jPanel3.add(name_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 220, 30));

        email_field.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        email_field.setText("Email");
        email_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email_fieldActionPerformed(evt);
            }
        });
        email_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldsChanged(evt);
            }
        });
        jPanel3.add(email_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 220, 30));

        username_field.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        username_field.setText("Username");
        username_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldsChanged(evt);
            }
        });
        jPanel3.add(username_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 220, 30));

        reset_button.setBackground(new java.awt.Color(255, 255, 255));
        reset_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Undo Changes");
        reset_button.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 30));

        jPanel3.add(reset_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 90, 30));

        change_pass_btn.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        change_pass_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        change_pass_btn.setText("Change Password");
        change_pass_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                change_pass_btnMouseClicked(evt);
            }
        });
        jPanel3.add(change_pass_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 110, 20));

        email_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        email_error.setForeground(new java.awt.Color(255, 0, 0));
        email_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(email_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 170, 10));

        username_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        username_error.setForeground(new java.awt.Color(255, 0, 0));
        username_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(username_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 170, 10));

        name_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        name_error.setForeground(new java.awt.Color(255, 0, 0));
        name_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(name_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 170, 10));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Email Not Verified. Verify Now");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 220, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldsChanged(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldsChanged
        
        String namee = name_field.getText().trim();
        String emaill = email_field.getText().trim();
        String usernamee = username_field.getText().trim();
        
        boolean valid_name = true;
        boolean valid_email = true;
        boolean valid_username = true;
        
        try {

            if(conn.fieldExists("username", usernamee) && !usernamee.equals(username)){
                setInvalidBorder(username_field);
                displayError(username_error, "Username Already Taken!");
                valid_username = false;
            }else if(usernamee.isEmpty()){
                setInvalidBorder(username_field);
                displayError(username_error, "Field Required!");
                valid_username = false;
            }else {
                resetBorder(username_field);
                displayError(username_error, "");
                valid_username = true;
            }

            if(conn.fieldExists("email", emaill) && !emaill.equals(email)){
                setInvalidBorder(email_field);
                displayError(email_error, "Email Already Exist!");
                valid_email = false;
            }else if(!Utility.isValidEmail(emaill)){
                setInvalidBorder(email_field);
                displayError(email_error, "Invalid!");
                valid_email = false;
            }else if(emaill.isEmpty()){
                setInvalidBorder(email_field);
                displayError(email_error, "Field Required!");
                valid_email = false;
            }else {
                resetBorder(email_field);
                displayError(email_error, "");
                valid_email = true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        if(namee.isEmpty()){
            setInvalidBorder(name_field);
            displayError(name_error, "Field Required!");
            valid_name = false;
        }else {
            resetBorder(name_field);
            displayError(name_error, "");
            valid_name = true;
        }
        
        valid_to_save = valid_username && valid_email && valid_name;
        
        boolean changed = !name_field.getText().trim().equals(this.name) ||
                            !email_field.getText().trim().equals(this.email) ||
                            !username_field.getText().trim().equals(this.username);

        save_button.setEnabled(changed && valid_to_save);
        reset_button.setEnabled(changed || !valid_to_save);
        
        if(changed && valid_to_save) {
            save_button.setBackground(Utility.blackish);
            jLabel2.setForeground(Color.WHITE);
        }else {
            save_button.setBackground(Color.WHITE);
            jLabel2.setForeground(Color.WHITE);
        }
        
        if(changed || !valid_to_save) {
            reset_button.setBackground(Utility.blackish);
            reset_button.setForeground(Color.WHITE);
        }else {
            reset_button.setBackground(Color.WHITE);
            jLabel6.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_fieldsChanged

    private void email_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email_fieldActionPerformed

    private void change_pass_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change_pass_btnMouseClicked
        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            change_pass.changePassDialog(desktopPane);
        }
    }//GEN-LAST:event_change_pass_btnMouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        jLabel7.setForeground(new Color(134,206,203));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setForeground(new Color(0,0,204));
    }//GEN-LAST:event_jLabel7MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel change_pass_btn;
    private javax.swing.JLabel email_error;
    private javax.swing.JTextField email_field;
    private javax.swing.JLabel email_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel name_error;
    private javax.swing.JTextField name_field;
    private javax.swing.JLabel name_label;
    private javax.swing.JPanel reset_button;
    private javax.swing.JPanel save_button;
    private javax.swing.JLabel user_pic;
    private javax.swing.JLabel username_error;
    private javax.swing.JTextField username_field;
    // End of variables declaration//GEN-END:variables
}
