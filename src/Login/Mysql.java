/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

    public static Connection getConnection() throws SQLException {
        // Connection details matching your Workbench setup
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/loginform?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String hostUsername = "root";
        String hostPassword = "Chay00))";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        Connection conn = DriverManager.getConnection(dbUrl, hostUsername, hostPassword);
        System.out.println("✓ Database connected!");
        return conn;
    }
}

