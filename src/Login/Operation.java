package login;
import javax.swing.*;
import java.sql.*;


public class Operation {
    public static boolean isLogin(String username, String password, String usertype, JFrame frame) {

        String sql = "SELECT uid, username, nickname, login_as FROM login " +
                "WHERE username = ? AND password = ? AND login_as = ?";

        try (Connection conn = Mysql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) { //safe from SQL injection

            // Set query parameters
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, usertype);
            
            //placeholder

            // Execute query
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Login successful - set session data
                    LoginSession.UID = rs.getInt("uid");
                    LoginSession.Username = rs.getString("username");
                    LoginSession.Nickname = rs.getString("nickname");
                    LoginSession.Usertype = rs.getString("login_as");
                    LoginSession.isLoggedIn = true;

                    // Log success
                    System.out.println("✓ Login successful!");
                    System.out.println("  UID: " + LoginSession.UID);
                    System.out.println("  Username: " + LoginSession.Username);
                    System.out.println("  Nickname: " + LoginSession.Nickname);
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

    public static void main(String[] args) {
        try {
            Connection conn = Mysql.getConnection();

            // Check if table exists and show structure
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "login", null);

            if (tables.next()) {
                System.out.println("✓ Table 'login' exists");

                // Show columns
                ResultSet columns = metaData.getColumns(null, null, "login", null);
                System.out.println("\nTable structure:");
                while (columns.next()) {
                    System.out.println("  - " + columns.getString("COLUMN_NAME") +
                            " (" + columns.getString("TYPE_NAME") + ")");
                }
                columns.close();
            } else {
                System.out.println("✗ Table 'login' does not exist!");
            }

            tables.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("✗ Connection test failed!");
            e.printStackTrace();
        }
    }
}
