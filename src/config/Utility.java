package config;

import java.awt.Color;
import java.awt.Image;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Utility {
    
    public static Color blackish = new Color(55,59,62);
    public static Color grayish = new Color(190,200,209);
    public static Color miku = new Color(134,206,203);
    public static Color darkermiku = new Color(19,122,127);
    public static Color pink = new Color(225,40,133);
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void setIcons(JLabel[] labels, String[] paths) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setIcon(resizeImage("/images/" + paths[i], labels[i]));
        }
    }

    public static ImageIcon resizeImage(String path, JLabel label) {
        int width = label.getSize().width - 15;
        int height = label.getSize().height - 15;
        
        ImageIcon icon = new ImageIcon(Utility.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    
    public static void setBorders(JTextField... fields) {
        for (JTextField field : fields) {
            field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Utility.darkermiku, 1), 
                new EmptyBorder(0, 3, 0, 0)
            ));
        }
    }
    
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    public static void setInvalidBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.RED, 2), 
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }

    public static void resetBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Utility.darkermiku, 1),
            new EmptyBorder(0, 3, 0, 0) 
        ));
    }
    
}

