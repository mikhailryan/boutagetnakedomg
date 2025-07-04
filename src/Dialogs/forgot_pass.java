/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Point;
import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class forgot_pass extends javax.swing.JDialog {
    
    private static forgot_pass instance;
    db_connector conn = new db_connector();
    static String usernamee;
    
    public forgot_pass(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        SwingUtilities.invokeLater(() -> getRootPane().requestFocus());
        
        setLabels();
        Utility.setBorders(username_field, email_field);

    }
    
    private static void setField(JTextField username_field){
        if(usernamee.isEmpty() || usernamee.equals("Enter username...")){
            username_field.setText("Username");
        }else {
            username_field.setText(usernamee);
            username_field.setForeground(Color.BLACK);
        }
    }
    
    private void setLabels() {
        JLabel[] labels = {close_button};
        String[] paths = {"close.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
    } 
    
    public static void forgotPassDialog(JFrame parentFrame, String username) {
        usernamee = username;
        
        
        if (instance == null) {
            instance = new forgot_pass(parentFrame, true);
            centerDialog(parentFrame);
        }
        
        setField(instance.username_field);
        
        if (!instance.isVisible()) {
            centerDialog(parentFrame);
            instance.setVisible(true);
        }
    }

    private static void centerDialog(JFrame parentFrame) {
        if (instance != null && parentFrame != null) {
            int x = parentFrame.getX() + (parentFrame.getWidth() - instance.getWidth()) / 2;
            int y = parentFrame.getY() + (parentFrame.getHeight() - instance.getHeight()) / 2;
            instance.setLocation(x, y);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        close_button = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        email_field = new javax.swing.JTextField();
        username_field = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        email_error = new javax.swing.JLabel();
        username_error = new javax.swing.JLabel();
        error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(55, 59, 62), 2));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(55, 59, 62));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_buttonMouseClicked(evt);
            }
        });
        jPanel1.add(close_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 40, 40));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reset password");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 420, 60));

        email_field.setForeground(new java.awt.Color(153, 153, 153));
        email_field.setText("Email");
        email_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                email_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                email_fieldFocusLost(evt);
            }
        });
        email_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                email_fieldKeyPressed(evt);
            }
        });
        jPanel3.add(email_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 280, 40));

        username_field.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        username_field.setForeground(new java.awt.Color(153, 153, 153));
        username_field.setText("Username");
        username_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                username_fieldFocusLost(evt);
            }
        });
        username_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_fieldActionPerformed(evt);
            }
        });
        username_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                username_fieldKeyPressed(evt);
            }
        });
        jPanel3.add(username_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 280, 40));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter your username and email so we can send");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 420, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("your reset code");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 420, 30));

        jPanel2.setBackground(new java.awt.Color(19, 122, 127));
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
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Send Code");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, -1, 190, 40));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 280, 40));

        email_error.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        email_error.setForeground(new java.awt.Color(255, 0, 0));
        email_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(email_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 190, 10));

        username_error.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        username_error.setForeground(new java.awt.Color(255, 0, 0));
        username_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(username_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 290, 190, 10));

        error.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        error.setForeground(new java.awt.Color(255, 0, 0));
        error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 280, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void close_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_buttonMouseClicked
        this.dispose();
    }//GEN-LAST:event_close_buttonMouseClicked

    private void username_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_username_fieldActionPerformed

    private void username_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username_fieldFocusGained
        if (username_field.getText().trim().equals("Username")) {
            username_field.setText("");  
            username_field.setForeground(Color.BLACK); 
        }
    }//GEN-LAST:event_username_fieldFocusGained

    private void username_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username_fieldFocusLost
        if (username_field.getText().trim().isEmpty()) {
            username_field.setText("Username");
            username_field.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_username_fieldFocusLost

    private void email_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_email_fieldFocusGained
        if (email_field.getText().trim().equals("Email")) {
            email_field.setText("");
            email_field.setForeground(Color.BLACK); 
        }
    }//GEN-LAST:event_email_fieldFocusGained

    private void email_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_email_fieldFocusLost
        if (email_field.getText().trim().isEmpty()) {
            email_field.setText("Email");
            email_field.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_email_fieldFocusLost

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        jPanel3.requestFocusInWindow();
        
        String username = username_field.getText().trim();
        String email = email_field.getText().trim();
        boolean valid = true;
        
        if(username.isEmpty() || username.equals("Username")){
            Utility.setInvalidBorder(username_field);
            username_error.setText("Field Required");
            valid = false;
        }
        if(email.isEmpty() || email.equals("Email")){
            Utility.setInvalidBorder(email_field);
            email_error.setText("Field Required");
            valid = false;
        }
        
        if(!valid){
            return;
        }
        
        String code = "";
        String query = "SELECT id, email, email_verified FROM user WHERE username = '" + username + "' AND email = '" + email + "'";
        try {
            ResultSet rs = conn.getData(query);
            if (rs.next()) {
                int userId = rs.getInt("id");
                String userEmail = rs.getString("email");
                boolean emailVerified = rs.getBoolean("email_verified"); // assuming 0 = not verified, 1 = verified
                conn.insertLog(userId, "Requested password reset code");
                
                if (emailVerified) {
                    // Proceed with sending reset code
                    code = String.format("%06d", new Random().nextInt(999999));
                    String updateQuery = "UPDATE user SET verification_code = '" + code + "' WHERE id = " + userId;
                    db_connector.updateDatabase(updateQuery);

                    this.dispose();
                    instance = null;

                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (parentFrame != null) {
                        reset_code.resetCodeDialog(parentFrame, userId);
                    }
                } else {
                    String verificationCode = String.format("%06d", new Random().nextInt(999999));
                    String updateVerification = "UPDATE user SET verification_code = '" + verificationCode + "' WHERE id = " + userId;
                    db_connector.updateDatabase(updateVerification);

                    CustomMessageDialog.showMessage(null, "Your email is not verified.\nPlease verify to continue.", "Verify Email");
                    
                    this.dispose();
                    instance = null;
                    
                    Window parentFrame = SwingUtilities.getWindowAncestor(this);
                    if (parentFrame != null) {
                        verify_email_fp.verifyEmailDialog(parentFrame, userId);
                    }
                    
                }   
            } else {
                error.setText("No match found for username and email.");
                Utility.setInvalidBorder(email_field);
                Utility.setInvalidBorder(username_field);
            }
        } catch (SQLException e) {
            CustomMessageDialog.showError(null, "Error checking user data.\nPlease try again.", "Error");
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void username_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_username_fieldKeyPressed
        username_error.setText("");
        email_error.setText("");
        
        Utility.resetBorder(email_field);
        Utility.resetBorder(username_field);
    }//GEN-LAST:event_username_fieldKeyPressed

    private void email_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_email_fieldKeyPressed
        username_error.setText("");
        email_error.setText("");
        
        Utility.resetBorder(email_field);
        Utility.resetBorder(username_field);
    }//GEN-LAST:event_email_fieldKeyPressed

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        jPanel2.setBackground(Utility.miku); 
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        jPanel2.setBackground(Utility.darkermiku); 
    }//GEN-LAST:event_jPanel2MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel close_button;
    private javax.swing.JLabel email_error;
    private javax.swing.JTextField email_field;
    private javax.swing.JLabel error;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel username_error;
    private javax.swing.JTextField username_field;
    // End of variables declaration//GEN-END:variables
}
