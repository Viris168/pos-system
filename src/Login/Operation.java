package login;
import javax.swing.*;
import java.sql.*;


public class Operation {
   public static boolean isLogin(String username, String password, String usertype, JFrame frame) {
    // SQL query to fetch only username and login_as
    String sql = "SELECT username, login_as FROM Users " +
                 "WHERE username = ? AND password = ? AND login_as = ?";

    try (Connection conn = Mysql.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) { // Safe from SQL injection

        // Set query parameters
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, usertype);

        // Execute query
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Login successful - set session data
                LoginSession.Username = rs.getString("username");
                LoginSession.Usertype = rs.getString("login_as");
                LoginSession.isLoggedIn = true;

                // Log success
                System.out.println("✓ Login successful!");
                System.out.println("  Username: " + LoginSession.Username);
                System.out.println("  Usertype: " + LoginSession.Usertype);

                return true;
            } else {
                // No matching user found
                System.out.println("✗ Login failed - Invalid credentials");
                return false;
            }
        }
    } catch (SQLException e) {
        // Database error
        System.err.println("✗ Database error during login:");
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
