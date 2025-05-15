package config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {
    
    db_connector conn = new db_connector();
    private static Session instance;
    private Integer userId;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
    
    public String getEmail(){
        String email = "";
        
        try {
            ResultSet result = conn.getData("SELECT email FROM user WHERE id = '"+ userId + "'");
            if(result.next()){
                email = result.getString("email");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return email;
    }
    
    public String getName() {
        String name = "";

        try {
            ResultSet result = conn.getData("SELECT name FROM user WHERE id = '" + userId + "'");
            if(result.next()) {
                name = result.getString("name");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return name;
    }
    
    public String getUsername() {
        String name = "";

        try {
            ResultSet result = conn.getData("SELECT username FROM user WHERE id = '" + userId + "'");
            if(result.next()) {
                name = result.getString("username");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return name;
    }
    
    public boolean isEmailVerified(){
        try {
            ResultSet result = conn.getData("SELECT email_verified FROM user WHERE id = '"+ userId + "'");
            if(result.next()){
                return result.getInt("email_verified") == 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return false;
    }
    
    public String getVerificationCode() {
        String code = "";
        int attempts = 0;

        while (attempts < 3) { 
            try {
                ResultSet result = conn.getData("SELECT verification_code FROM user WHERE id = '" + userId + "'");
                if (result.next()) {
                    code = result.getString("verification_code");
                    if (code != null && !code.isEmpty()) {
                        return code;
                    }
                }
                Thread.sleep(200);  
                attempts++;
            } catch (SQLException | InterruptedException ex) {
                System.out.println("Error retrieving verification code: " + ex.getMessage());
                break;
            }
        }
        return code;
    }

    public void clearSession() {
        userId = null;
    }

    public boolean isLoggedIn() {
        return userId != null;
    }
}
