package config;

import Dialogs.CustomMessageDialog;
import Dialogs.CustomYesNoDialog;
import java.awt.Color;
import java.awt.Image;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import theShop.Login;

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
    
    public static void logout(JFrame parentFrame) {
        boolean confirm = CustomYesNoDialog.showConfirm(parentFrame, "Are you sure you want to log out?", "Logout Confirmation");

        if (confirm) {
            Session session = Session.getInstance();
            int userId = session.getUserId();
            db_connector conn = new db_connector();
            String role = conn.getUserRoleById(userId);

            conn.insertLog(userId, "User (" + role + ") logged out");
            session.clearSession();

            parentFrame.dispose();
            new Login().setVisible(true);
        }
    }
    
    public static List<String> getOutOfStockProducts(int sellerId) {
        List<String> list = new ArrayList<>();
        db_connector dbcon = new db_connector();
        String query = "SELECT name FROM products WHERE seller_id = " + sellerId + " AND stock = 0";

        try {
            ResultSet rs = dbcon.getData(query);
            while (rs.next()) list.add(rs.getString("name"));
        } catch (SQLException e) {
            System.out.println("Out-of-stock check failed: " + e.getMessage());
        }
        return list;
    }


    public static List<String> getLowStockProducts(int sellerId, int threshold) {
        List<String> list = new ArrayList<>();
        db_connector dbcon = new db_connector();
        String query = "SELECT name, stock FROM products WHERE seller_id = " + sellerId +
                       " AND stock > 0 AND stock <= " + threshold;

        try {
            ResultSet rs = dbcon.getData(query);
            while (rs.next()) {
                String name = rs.getString("name");
                int qty = rs.getInt("stock");
                list.add(name + " (Qty: " + qty + ")");
            }
        } catch (SQLException e) {
            System.out.println("Low stock check failed: " + e.getMessage());
        }
        return list;
    }
    
    public static void showStockAlert(int sellerId, JFrame parentFrame) {
        db_connector dbcon = new db_connector();
        List<String> outOfStock = new ArrayList<>();
        List<String> lowStock = new ArrayList<>();

        try {
            // Out of Stock
            ResultSet rs1 = dbcon.getData("SELECT name FROM products WHERE seller_id = " + sellerId + " AND stock = 0");
            while (rs1.next()) {
                outOfStock.add(rs1.getString("name"));
            }

            // Low Stock (threshold: 5)
            ResultSet rs2 = dbcon.getData("SELECT name FROM products WHERE seller_id = " + sellerId + " AND stock > 0 AND stock <= 5");
            while (rs2.next()) {
                lowStock.add(rs2.getString("name"));
            }

            if (!outOfStock.isEmpty() || !lowStock.isEmpty()) {
                StringBuilder alert = new StringBuilder();

                if (!outOfStock.isEmpty()) {
                    alert.append("🚫 Out of Stock:<br>");
                    for (String item : outOfStock) alert.append("• ").append(item).append("<br>");
                }

                if (!lowStock.isEmpty()) {
                    alert.append("<br>⚠️ Low Stock:<br>");
                    for (String item : lowStock) alert.append("• ").append(item).append("<br>");
                }

                CustomMessageDialog.showError(parentFrame, alert.toString(), "Stock Alert");
            }

        } catch (SQLException e) {
            System.out.println("Stock alert error: " + e.getMessage());
        }
    }
    
    public static void showIndividualStockAlerts(int sellerId, JFrame parentFrame) {
        db_connector dbcon = new db_connector();

        try {
            ResultSet rsOut = dbcon.getData("SELECT name FROM products WHERE seller_id = " + sellerId + " AND stock = 0");
            while (rsOut.next()) {
                String productName = rsOut.getString("name");
                String message = "Product \"" + productName + "\" is OUT OF STOCK!";
                CustomMessageDialog.showError(parentFrame, message, "Stock Alert");
            }

            ResultSet rsLow = dbcon.getData("SELECT name, stock FROM products WHERE seller_id = " + sellerId + " AND stock > 0 AND stock <= 10");
            while (rsLow.next()) {
                String productName = rsLow.getString("name");
                int qty = rsLow.getInt("stock");
                String message = "Product \"" + productName + "\" is LOW ON STOCK (" + qty + " remaining)";
                CustomMessageDialog.showError(parentFrame, message, "Stock Alert");
            }

        } catch (SQLException e) {
            System.out.println("Error showing stock alerts: " + e.getMessage());
        }
    }

}

