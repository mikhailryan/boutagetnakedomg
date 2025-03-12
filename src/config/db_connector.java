package config;

import java.sql.*;

public class db_connector {

    private static Connection conn;
    
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
    
    public ResultSet getData(String sql) throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;
    }
    
    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet result = pstmt.executeQuery();

        return result.next(); 
    }
    
    public boolean fieldExists(String fieldName, String value) throws SQLException {
        String sql = "SELECT * FROM user WHERE " + fieldName + " = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, value);
        ResultSet result = pstmt.executeQuery();

        return result.next(); 
    }
    
    
    public static boolean updateDatabase(String sql) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error in Updating: " + e.getMessage());
            return false;
        }
    }

    
    /*
    private void display_data(){
        try {
            db_connector dbcon = new db_connector();
            ResultSet result = dbcon.get_data("SELECT * FROM table_name");
            table_name.setModel(DbUtils.resultSetToTableModel(result));
        } catch (SQLException e) {
            System.out.println("Can't Connect to Database: " + e.getMessage());
        }
        
    }
    */
    public int insertData(String sql){
        int result;
        try{
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.executeUpdate();
                System.out.println("Inserted Successfully!");
            }
            result = 1;
        }catch(SQLException e){
            System.out.println("Connection Error: " + e.getMessage());
            result = 0;
        }
        return result;
    }
    
}
