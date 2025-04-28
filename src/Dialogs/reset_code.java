/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import static Dialogs.forgot_pass.usernamee;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Frame;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class reset_code extends javax.swing.JDialog {
    
    db_connector conn = new db_connector();
    private static reset_code instance;
    static int userId;
    
    /**
     * Creates new form reset_code
     */
    public reset_code(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        setLabels();
        Utility.setBorders(code_field);
        
        Session.getInstance().setUserId(userId); 
    }
    
    public static void resetCodeDialog(Frame parentDialog, int id) {
        userId = id;

        if (instance == null) {
            instance = new reset_code(parentDialog, true);
            centerDialog(parentDialog);
        }

        if (!instance.isVisible()) {
            centerDialog(parentDialog);
            instance.setVisible(true);
        }
    }

    private static void centerDialog(Frame parentDialog) {
        if (instance != null && parentDialog != null) {
            int x = parentDialog.getX() + (parentDialog.getWidth() - instance.getWidth()) / 2;
            int y = parentDialog.getY() + (parentDialog.getHeight() - instance.getHeight()) / 2;
            instance.setLocation(x, y);
        }
    }

    
    private void setLabels() {
        JLabel[] labels = {close_button};
        String[] paths = {"close.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        close_button = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        code_field = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        code_error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(55, 59, 62), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 59, 62));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_buttonMouseClicked(evt);
            }
        });
        jPanel2.add(close_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 40, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Check your inbox");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 420, 60));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter the code we sent to reset your password");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 420, 30));

        code_field.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        code_field.setForeground(new java.awt.Color(153, 153, 153));
        code_field.setText("Reset Code");
        code_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                code_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                code_fieldFocusLost(evt);
            }
        });
        code_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                code_fieldActionPerformed(evt);
            }
        });
        code_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                code_fieldKeyPressed(evt);
            }
        });
        jPanel1.add(code_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 280, 40));

        jPanel3.setBackground(new java.awt.Color(19, 122, 127));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Submit Code");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, -1, 190, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 280, 40));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Resend Code");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 280, -1));

        code_error.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        code_error.setForeground(new java.awt.Color(255, 0, 0));
        code_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(code_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 330, 210, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void close_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_buttonMouseClicked
        this.dispose();
        instance = null;
    }//GEN-LAST:event_close_buttonMouseClicked

    private void code_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_code_fieldFocusGained
        if (code_field.getText().trim().equals("Reset Code")) {
            code_field.setText("");
            code_field.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_code_fieldFocusGained

    private void code_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_code_fieldFocusLost
        if (code_field.getText().trim().isEmpty()) {
            code_field.setText("Reset Code");
            code_field.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_code_fieldFocusLost

    private void code_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_code_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_code_fieldActionPerformed

    private void code_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_code_fieldKeyPressed
        code_error.setText("");
        Utility.resetBorder(code_field);
    }//GEN-LAST:event_code_fieldKeyPressed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        jPanel3.requestFocusInWindow();

        String code = code_field.getText().trim();

        if(code.isEmpty() || code.equals("Reset Code")){
            Utility.setInvalidBorder(code_field);
            code_error.setText("Field Required");
            return;
        }
        
        String query = "SELECT verification_code FROM user WHERE id = '"+ userId +"'";
        try {
            
            ResultSet rs = conn.getData(query);

            if (rs.next()) {
                int db_code = rs.getInt("verification_code");
                
                if (String.valueOf(db_code).equals(code)) {
                    CustomMessageDialog.showMessage(null, "Code verified! You can now reset your password.", "Success");
                    String clearCodeQuery = "UPDATE user SET verification_code = NULL WHERE id = '" + userId + "'";
                    db_connector.updateDatabase(clearCodeQuery);
                    
                    this.dispose(); 
                    instance = null;
                    
                    new_pass.newPassDialog(this);
                    
                } else {
                    Utility.setInvalidBorder(code_field);
                    code_error.setText("Incorrect reset code.");
                }
                
            } else {
                code_error.setText("Invalid reset code.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking reset code. Please try again.");
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel close_button;
    private javax.swing.JLabel code_error;
    private javax.swing.JTextField code_field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
