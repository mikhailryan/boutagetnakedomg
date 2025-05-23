package Dialogs;

import config.EmailSender;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public final class verify_email_fp extends javax.swing.JDialog {
    
    private static verify_email_fp instance;
    db_connector conn = new db_connector();
    private int userId;
    private String userEmail;
    private String code;
    
    /**
     * Creates new form verify_email
     * @param parent
     * @param modal
     * @param email
     * @param id
     */
    public verify_email_fp(java.awt.Window parent, boolean modal, int id) {
        super(parent, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();

        Session.getInstance().setUserId(id);

        Utility.setBorders(code_input);
        setLabels();
        unFocus();  
        setEmail();

        code_input.setText("Code");

        EmailSender.sendVerificationCodeEmail(Session.getInstance().getEmail(), Integer.parseInt(Session.getInstance().getVerificationCode()));
    }

    
    public void setEmail(){
        email_label.setText(Session.getInstance().getEmail());
    }
    
    private void setLabels() {
        JLabel[] labels = {close_button};
        String[] paths = {"close.png"};
        SwingUtilities.invokeLater(() -> {
            Utility.setIcons(labels, paths);
        });
    } 
    
    public static void verifyEmailDialog(Window parentWindow, int id) {
        if (instance == null) { 
            if (parentWindow != null) {
                instance = new verify_email_fp(parentWindow, true, id);
                centerDialog(parentWindow);
            } else {
                System.err.println("Error: Parent window not found!");
                return;
            }
        }

        if (!instance.isVisible()) {
            centerDialog(parentWindow);
            instance.resetFields();
            instance.setVisible(true);
        }
    }

    private static void centerDialog(Window parentWindow) {
        if (instance != null && parentWindow != null) {
            instance.setLocationRelativeTo(parentWindow);
        }
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
        email_label = new javax.swing.JLabel();
        code_input = new javax.swing.JTextField();
        verify_button = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        close_button = new javax.swing.JLabel();
        code_error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(55, 59, 62), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Check your inbox");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 440, 60));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter the verification code we just sent to");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 440, 30));

        email_label.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        email_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        email_label.setText("email");
        jPanel1.add(email_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 440, -1));

        code_input.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        code_input.setForeground(new java.awt.Color(153, 153, 153));
        code_input.setText("Code");
        code_input.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                code_inputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                code_inputFocusLost(evt);
            }
        });
        code_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                code_inputKeyReleased(evt);
            }
        });
        jPanel1.add(code_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 260, 40));

        verify_button.setBackground(new java.awt.Color(19, 122, 127));
        verify_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verify_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verify_buttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verify_buttonMouseExited(evt);
            }
        });
        verify_button.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Verify");
        verify_button.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 0, 210, 40));

        jPanel1.add(verify_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 260, 40));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Resend Code");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, 160, 20));

        jPanel2.setBackground(new java.awt.Color(55, 59, 62));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_buttonMouseClicked(evt);
            }
        });
        jPanel2.add(close_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 40, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 40));

        code_error.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        code_error.setForeground(new java.awt.Color(255, 0, 0));
        code_error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(code_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 320, 190, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void verify_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verify_buttonMouseEntered
        verify_button.setBackground(Utility.miku);
    }//GEN-LAST:event_verify_buttonMouseEntered

    private void verify_buttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verify_buttonMouseExited
        verify_button.setBackground(Utility.darkermiku);
    }//GEN-LAST:event_verify_buttonMouseExited

    private void close_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_buttonMouseClicked
        this.dispose();
        instance = null;
    }//GEN-LAST:event_close_buttonMouseClicked

    private void verify_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verify_buttonMouseClicked
        String code = code_input.getText().trim();
        String code_from_db = Session.getInstance().getVerificationCode();
        
        if(code.isEmpty()){
            Utility.setInvalidBorder(code_input);
            code_error.setText("Please enter the code.");
            return;
        }
        
        if(code.equals(code_from_db)){
            boolean success = db_connector.updateDatabase("UPDATE user SET email_verified = 1, verification_code = NULL WHERE id = '"+ Session.getInstance().getUserId() + "'");
            if(success){
                CustomMessageDialog.showMessage(null, "Your email is now verified", "Email Verification");
                this.dispose();
                instance = null;
                
                
                
                new_pass.newPassDialog(this);
            }
        }else {
            Utility.setInvalidBorder(code_input);
            code_error.setText("Invalid code.");
        }
        
    }//GEN-LAST:event_verify_buttonMouseClicked
    
    private void resetFields() {
        code_input.setText("Code");
        code_input.setForeground(new Color(153,153,153));
        code_error.setText("");
        Utility.resetBorder(code_input);
    }
    
    private void code_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_code_inputKeyReleased
        Utility.resetBorder(code_input);
        code_error.setText("");
    }//GEN-LAST:event_code_inputKeyReleased

    private void code_inputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_code_inputFocusGained
        if(code_input.getText().equals("Code")){
            code_input.setText("");
            code_input.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_code_inputFocusGained

    private void code_inputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_code_inputFocusLost
        if(code_input.getText().isEmpty()){
            code_input.setText("Code");
            code_input.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_code_inputFocusLost

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel close_button;
    private javax.swing.JLabel code_error;
    private javax.swing.JTextField code_input;
    private javax.swing.JLabel email_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel verify_button;
    // End of variables declaration//GEN-END:variables
}
