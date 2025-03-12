package config;

import java.awt.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    
    
}

