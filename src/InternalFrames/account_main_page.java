/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalFrames;

import Dialogs.CustomYesNoDialog;
import Dialogs.change_pass;
import Dialogs.verify_email;
import config.Session;
import config.Utility;
import config.db_connector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.sql.*;
import javax.swing.ImageIcon;

/**
 *
 * @author SCC-COLLEGE
 */
public class account_main_page extends javax.swing.JInternalFrame {
    
    private JInternalFrame active_frame = null;
    
    int id = Session.getInstance().getUserId();
    String name = "";
    String email = "";  
    String username = "";
    
    db_connector conn = new db_connector();
    Connection connn = conn.getConnection();
    
    /**
     * Creates new form account_profile
     */
    public account_main_page() {
        initComponents();
        
        active_frame = this;
        
        unFocus();
        
        getData();
        addPanelMouseListener();
        setEmail();
        displayProfilePicture();
        
//        setLabels();
        Utility.setBorders(name_field, email_field, username_field);
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
    }
    
    private void uploadProfilePicture(String filePath) {
        try {   
            File selectedFile = new File(filePath);
            if (!selectedFile.exists()) {
                throw new IllegalArgumentException("File does not exist.");
            }

            int userId = Session.getInstance().getUserId();
            String oldImagePath = null;

            String query = "SELECT profile_pic FROM user WHERE id = ?";
            PreparedStatement getStmt = connn.prepareStatement(query);
            getStmt.setInt(1, userId);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                oldImagePath = rs.getString("profile_pic");
            }

            String targetDir = "ProfilePics";
            File targetFolder = new File(targetDir);
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }   

            String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
            File targetFile = new File(targetFolder, newFileName);

            Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String imagePath = "ProfilePics/" + newFileName;
            String updateSQL = "UPDATE user SET profile_pic = ? WHERE id = ?";
            PreparedStatement updateStmt = connn.prepareStatement(updateSQL);
            updateStmt.setString(1, imagePath);
            updateStmt.setInt(2, userId);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Profile picture updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // ✅ Delete old image if it's not the default
            if (oldImagePath != null && !oldImagePath.equals("ProfilePics/user_pic.jpg")) {
                File oldFile = new File(System.getProperty("user.dir"), oldImagePath);
                if (oldFile.exists()) {
                    boolean deleted = oldFile.delete();
                }
            }

            displayProfilePicture(); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error uploading profile picture: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void displayProfilePicture() {
        try {
            // Query to get the profile picture path from the database
            String query = "SELECT profile_pic FROM user WHERE id = ?";
            PreparedStatement stmt = connn.prepareStatement(query);
            stmt.setInt(1, Session.getInstance().getUserId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String profilePicPath = rs.getString("profile_pic");

                // If the profile picture path is null or empty, use the default image
                if (profilePicPath == null || profilePicPath.isEmpty()) {
                    profilePicPath = "ProfilePics/user.png"; // Default image path
                }

                // Check if the file exists in the directory
                File imgFile = new File(profilePicPath);
                if (!imgFile.exists()) {
                    // If the file doesn't exist, use the default image
                    imgFile = new File("ProfilePics/user.png");
                }

                // Load and scale the image
                ImageIcon profilePic = new ImageIcon(imgFile.getAbsolutePath());
                Image image = profilePic.getImage().getScaledInstance(110, 100, Image.SCALE_SMOOTH);
                user_pic.setIcon(new ImageIcon(image)); // Set the scaled image as the icon
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    public final void setEmail(){
        if (Session.getInstance().isEmailVerified()) {
            jLabel7.setText("Email Verified.");
            jLabel7.setEnabled(false);
        }else {
            jLabel7.setText("Email Not Verified. Verify Now");
            jLabel7.setEnabled(true);
        }
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
                            + "email = '"+email+"', "
                            + "verification_code = NULL, "
                            + "email_verified = '"+0+"' "
                                + "WHERE id = '"+id+"'");

                    JOptionPane.showMessageDialog(null, "Edited Successfully!");
                    getData();
                    setEmail();
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
        jPanel2 = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setPreferredSize(new java.awt.Dimension(610, 500));

        jPanel1.setBackground(new java.awt.Color(134, 206, 203));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 470));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user_pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        user_pic.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true));
        user_pic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                user_picMouseClicked(evt);
            }
        });
        jPanel3.add(user_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 120, 110));

        jPanel4.setBackground(Utility.darkermiku);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 90));

        name_label.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        name_label.setText("Name");
        jPanel3.add(name_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 350, 30));

        email_label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        email_label.setText("Email");
        jPanel3.add(email_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 330, 30));

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
                name_fieldfieldsChanged(evt);
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
                email_fieldfieldsChanged(evt);
            }
        });
        jPanel3.add(email_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 220, 30));

        username_field.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        username_field.setText("Username");
        username_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                username_fieldfieldsChanged(evt);
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 220, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 420, 400));

        jPanel2.setBackground(new java.awt.Color(55, 59, 62));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 610, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void name_fieldfieldsChanged(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_name_fieldfieldsChanged

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
    }//GEN-LAST:event_name_fieldfieldsChanged

    private void email_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email_fieldActionPerformed

    private void email_fieldfieldsChanged(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_email_fieldfieldsChanged

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
    }//GEN-LAST:event_email_fieldfieldsChanged

    private void username_fieldfieldsChanged(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_username_fieldfieldsChanged

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
    }//GEN-LAST:event_username_fieldfieldsChanged

    private void change_pass_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_change_pass_btnMouseClicked
        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            change_pass.changePassDialog(desktopPane);
        }
    }//GEN-LAST:event_change_pass_btnMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if(!jLabel7.isEnabled()){
            return;
        }

        String code = String.format("%06d", new Random().nextInt(999999));
        db_connector.updateDatabase("UPDATE user SET verification_code = '"+ code +"' WHERE id = '"+ id + "'");

        JDesktopPane desktopPane = this.getDesktopPane();
        if (desktopPane != null) {
            verify_email.verifyEmailDialog(desktopPane, null);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        jLabel7.setForeground(new Color(134,206,203));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setForeground(new Color(0,0,204));
    }//GEN-LAST:event_jLabel7MouseExited

    private void user_picMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_user_picMouseClicked
        boolean changePP = CustomYesNoDialog.showConfirm(null, "Change Profile Picture?", "Profile Picture");
        
        if(!changePP) { return; }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Profile Picture");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            // Now you have the file path and can proceed to upload
            uploadProfilePicture(filePath);
        }
    }//GEN-LAST:event_user_picMouseClicked

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
    private javax.swing.JPanel jPanel2;
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
