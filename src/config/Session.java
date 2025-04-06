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
    
    public String getVerificationCode(){
        String code = "";
        
        try {
            ResultSet result = conn.getData("SELECT verification_code FROM user WHERE id = '"+ userId + "'");
            if(result.next()){
                code = result.getString("verification_code");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
