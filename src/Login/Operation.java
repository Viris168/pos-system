package login;
import javax.swing.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class Operation {
    
    // Method to hash the password using bcrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to check if the entered password matches the stored hash
    public static boolean checkPassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash); // Compare entered password with stored hash
    }
    
   public static boolean isLogin(String username, String password, String usertype, JFrame frame) {
    // SQL query to fetch only username and login_as
    String sql = "SELECT username, password, login_as FROM Users " +
                "WHERE username = ? AND login_as = ?";

    try (Connection conn = Mysql.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) { // Safe from SQL injection

        // Set query parameters
        ps.setString(1, username);
        ps.setString(2, usertype);

        // Execute query
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                
                String storedHash = rs.getString("password");
                
                if (BCrypt.checkpw(password, storedHash)) {
                        LoginSession.Username = rs.getString("username");
                        LoginSession.Usertype = rs.getString("login_as");
                        LoginSession.isLoggedIn = true;
                        
                        System.out.println(" Login successful!");
                        System.out.println("  Username: " + LoginSession.Username);
                        System.out.println("  Usertype: " + LoginSession.Usertype);
                        return true;
                    } else {
                        
                        System.out.println(" Login failed - Invalid password");
                        return false;
                    }
            } else {
                
                System.out.println(" Login failed - Invalid credentials");
                return false;
            }
        }
    } catch (SQLException e) {
        // Database error
        System.err.println(" Database error during login:");
        System.err.println("  Error: " + e.getMessage());
        e.printStackTrace();

        // Show user-friendly error message
        JOptionPane.showMessageDialog(frame,
                "Database connection error!\n\n" +
                        "Please check:\n" +
                        "• MySQL server is running\n" +
                        "• Database 'loginform' exists\n" +
                        "• Table 'login' exists with correct structure\n\n" +
                        "Error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);

        return false;
    }
}

}
