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
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FocusTraversalPolicy;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import theShop.Login;

public class change_pass extends javax.swing.JDialog {
    
    private static change_pass instance;  
    private final db_connector conn = new db_connector();
    private int id = -1;
    String current_pass;
    
    boolean valid_to_change = true;
    boolean valid = false;
    
    /**
     * Creates new form change_pass
     * @param parent
     * @param modal
     */
    public change_pass(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        resetFields();
        unFocus();
        Utility.setBorders(currentPass_field, newPass_field, newPass2_field);
        
        JLabel[] labels = {close_button, show_pass, show_pass1, show_pass2};
        String[] paths = {"close.png", "hidden.png", "hidden.png", "hidden.png"};
        Utility.setIcons(labels, paths);
        
        id = Session.getInstance().getUserId();
        
        try {
            ResultSet result = conn.getData("SELECT password FROM user WHERE id = " + id);
            if(result.next()){
                current_pass = result.getString("password");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        change_button.setEnabled(false);
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
    
    public static void changePassDialog(JDesktopPane desktopPane) {
        if (instance == null) { 
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(desktopPane);
            if (parentFrame != null) {
                instance = new change_pass(parentFrame, true);
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
    
    public final void resetFields() {
        newPass_field.setText(""); 
        newPass2_field.setText(""); 
        currentPass_field.setText("");

        displayError(newPass_error, ""); 
        displayError(newPass2_error, ""); 
        displayError(currentPass_error, ""); 

        resetBorder(newPass_field);
        resetBorder(newPass2_field);
        resetBorder(currentPass_field);

        valid = false; 
        valid_to_change = false; 
    }
    
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            resetFields(); 
        }
        super.setVisible(visible);
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
        jPanel4 = new javax.swing.JPanel();
        close_button = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        show_pass = new javax.swing.JLabel();
        show_pass1 = new javax.swing.JLabel();
        show_pass2 = new javax.swing.JLabel();
        currentPass_field = new javax.swing.JPasswordField();
        newPass_field = new javax.swing.JPasswordField();
        newPass2_field = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        change_button = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        currentPass_error = new javax.swing.JLabel();
        newPass_error = new javax.swing.JLabel();
        newPass2_error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel4.setBackground(Utility.darkermiku);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_buttonMouseClicked(evt);
            }
        });
        jPanel4.add(close_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(Utility.blackish);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Changing Password");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 410, 50));

        jPanel1.add(jPanel4);
        jPanel4.setBounds(0, 0, 410, 90);

        show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hidden.png"))); // NOI18N
        show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_passMouseClicked(evt);
            }
        });
        jPanel1.add(show_pass);
        show_pass.setBounds(350, 250, 30, 30);

        show_pass1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hidden.png"))); // NOI18N
        show_pass1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_pass1MouseClicked(evt);
            }
        });
        jPanel1.add(show_pass1);
        show_pass1.setBounds(350, 300, 30, 30);

        show_pass2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hidden.png"))); // NOI18N
        show_pass2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_pass2MouseClicked(evt);
            }
        });
        jPanel1.add(show_pass2);
        show_pass2.setBounds(350, 150, 30, 30);

        currentPass_field.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        currentPass_field.setEchoChar((char) '•');
        currentPass_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                currentPass_fieldKeyReleased(evt);
            }
        });
        jPanel1.add(currentPass_field);
        currentPass_field.setBounds(200, 150, 170, 30);

        newPass_field.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        newPass_field.setEchoChar((char) '•');
        newPass_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPass_fieldKeyReleased(evt);
            }
        });
        jPanel1.add(newPass_field);
        newPass_field.setBounds(200, 250, 170, 30);

        newPass2_field.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        newPass2_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPass2_fieldKeyReleased(evt);
            }
        });
        newPass2_field.setEchoChar((char) '•');
        jPanel1.add(newPass2_field);
        newPass2_field.setBounds(200, 300, 170, 30);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel1.setText("Current Password");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 150, 140, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel2.setText("New Password");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(40, 250, 140, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel3.setText("Confirm New Password");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 300, 140, 30);

        jLabel5.setBackground(Utility.darkermiku);
        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("New Password");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(0, 210, 410, 20);

        jPanel2.setBackground(Utility.darkermiku);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(250, 220, 160, 2);

        change_button.setBackground(Utility.blackish);
        change_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                change_buttonMouseClicked(evt);
            }
        });
        change_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel6.setForeground(Utility.grayish);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Change Password");
        change_button.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jPanel1.add(change_button);
        change_button.setBounds(150, 350, 120, 30);

        jPanel5.setBackground(Utility.darkermiku);
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(0, 120, 160, 2);

        jPanel6.setBackground(Utility.darkermiku);
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6);
        jPanel6.setBounds(250, 120, 160, 2);

        jLabel7.setBackground(Utility.darkermiku);
        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Old Password");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 110, 410, 20);

        jPanel7.setBackground(Utility.darkermiku);
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7);
        jPanel7.setBounds(0, 220, 160, 2);

        currentPass_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        currentPass_error.setForeground(new java.awt.Color(255, 0, 0));
        currentPass_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(currentPass_error);
        currentPass_error.setBounds(230, 180, 140, 10);

        newPass_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        newPass_error.setForeground(new java.awt.Color(255, 0, 0));
        newPass_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(newPass_error);
        newPass_error.setBounds(230, 280, 140, 10);

        newPass2_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        newPass2_error.setForeground(new java.awt.Color(255, 0, 0));
        newPass2_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(newPass2_error);
        newPass2_error.setBounds(230, 330, 140, 10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void close_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_buttonMouseClicked
        this.dispose();
    }//GEN-LAST:event_close_buttonMouseClicked
         
    private void change_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change_buttonMouseClicked
        if (!change_button.isEnabled()) {
            return; 
        }
        
        String hashedNewPass = Utility.hashPassword(newPass_field.getText().trim());
        if (hashedNewPass.equals(current_pass)) {
            CustomMessageDialog.showError(null, "New password cannot be the same as the old password. Please choose a different one.", "Error");
            return;
        }
        
        boolean confirmed = CustomYesNoDialog.showConfirm(null, "Changing your password will log you out.\nDo you want to continue?", "Confirm Change Password");
        if (!confirmed) {
            return;
        }

        String sql = "UPDATE user SET password = '" + hashedNewPass + "' WHERE id = '" + id + "'";
        boolean success = db_connector.updateDatabase(sql);

        if (success) {
            CustomMessageDialog.showMessage(null, "Password Changed Successfully!\nLogging Out.", "Success");

            Session session = Session.getInstance();
            session.clearSession();

            Window parentWindow = SwingUtilities.getWindowAncestor(change_button);
            if (parentWindow instanceof JDialog) {
                parentWindow.dispose();
            }

            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(parentWindow);
            if (mainFrame != null) {
                mainFrame.dispose();
            }

            new Login().setVisible(true);
        } else {
            CustomMessageDialog.showMessage(null, "Failed to update password.\nTry again.", "Error");
        }
    }//GEN-LAST:event_change_buttonMouseClicked
    
    boolean pass_visible = false;
    private void show_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_passMouseClicked
        pass_visible = !pass_visible;
        if (pass_visible) {
            newPass_field.setEchoChar((char) 0);
            show_pass.setIcon(Utility.resizeImage("/images/eye.png", show_pass));
        } else {
            newPass_field.setEchoChar('•');
            show_pass.setIcon(Utility.resizeImage("/images/hidden.png", show_pass));
        }
    }//GEN-LAST:event_show_passMouseClicked
    
    boolean pass2_visible = false;   
    private void show_pass1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_pass1MouseClicked
        pass2_visible = !pass2_visible;
        if (pass2_visible) {
            newPass2_field.setEchoChar((char) 0);
            show_pass1.setIcon(Utility.resizeImage("/images/eye.png", show_pass));
        } else {
            newPass2_field.setEchoChar('•');
            show_pass1.setIcon(Utility.resizeImage("/images/hidden.png", show_pass));
        }
    }//GEN-LAST:event_show_pass1MouseClicked
    boolean pass1_visible = false;  
    private void show_pass2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_pass2MouseClicked
        pass1_visible = !pass1_visible;
        if (pass1_visible) {
            currentPass_field.setEchoChar((char) 0);
            show_pass2.setIcon(Utility.resizeImage("/images/eye.png", show_pass));
        } else {
            currentPass_field.setEchoChar('•');
            show_pass2.setIcon(Utility.resizeImage("/images/hidden.png", show_pass));
        }
    }//GEN-LAST:event_show_pass2MouseClicked

    private void currentPass_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_currentPass_fieldKeyReleased
        validateFields();
    }//GEN-LAST:event_currentPass_fieldKeyReleased

    private void newPass_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPass_fieldKeyReleased
        validateFields();
    }//GEN-LAST:event_newPass_fieldKeyReleased

    private void newPass2_fieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPass2_fieldKeyReleased
        validateFields();
    }//GEN-LAST:event_newPass2_fieldKeyReleased
    
    private void validateFields() {
        
        boolean valid = true;

        // Get input values
        String currentPassInput = new String(currentPass_field.getPassword()).trim();
        String hashedPassInput = Utility.hashPassword(currentPassInput);

        String newPassInput = new String(newPass_field.getPassword()).trim();
        String newPass2Input = new String(newPass2_field.getPassword()).trim();

        if (currentPassInput.isEmpty()) {
            valid = false;
            setInvalidBorder(currentPass_field);
            displayError(currentPass_error, "Please enter old password!");
        } else if (!hashedPassInput.equals(current_pass)) {
            valid = false;
            setInvalidBorder(currentPass_field);
            displayError(currentPass_error, "Wrong password!");
        } else {
            resetBorder(currentPass_field);
            displayError(currentPass_error, "");
        }

        if (valid) {
            if (newPassInput.isEmpty()) {
                valid = false;
                setInvalidBorder(newPass_field);
                displayError(newPass_error, "Field required!");
            } else if (newPassInput.length() < 8) {
                valid = false;
                setInvalidBorder(newPass_field);
                displayError(newPass_error, "New password too short!");
            } else {
                resetBorder(newPass_field);
                displayError(newPass_error, "");
            }

            if (newPass2Input.isEmpty()) {
                valid = false;
                setInvalidBorder(newPass2_field);
                displayError(newPass2_error, "Field required!");
            } else if (!newPassInput.equals(newPass2Input)) {
                valid = false;
                setInvalidBorder(newPass2_field);
                displayError(newPass2_error, "Passwords do not match!");
            } else {
                resetBorder(newPass2_field);
                displayError(newPass2_error, "");
            }
        }

        change_button.setEnabled(valid);
        change_button.setCursor(valid ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel change_button;
    private javax.swing.JLabel close_button;
    private javax.swing.JLabel currentPass_error;
    private javax.swing.JPasswordField currentPass_field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel newPass2_error;
    private javax.swing.JPasswordField newPass2_field;
    private javax.swing.JLabel newPass_error;
    private javax.swing.JPasswordField newPass_field;
    private javax.swing.JLabel show_pass;
    private javax.swing.JLabel show_pass1;
    private javax.swing.JLabel show_pass2;
    // End of variables declaration//GEN-END:variables
}
