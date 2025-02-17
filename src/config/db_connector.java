package config;

import java.sql.*;

public class db_connector {
    private Connection conn;
    
    public db_connector(){
        String url = "jdbc:mysql://localhost:3306/isthisadatabase";
        String user = "root";
        String password = "";
        
        try {
            
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Can't Connect to Database: " + e.getMessage());
        }
        
    }
    
    public ResultSet get_data(String sql) throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;
    }

}
