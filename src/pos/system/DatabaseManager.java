
package pos.system;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pos_system";  // Correct database name
    private static final String USER = "root";  // MySQL username
    private static final String PASS = "Chay00))";      // MySQL password

    // Fetch the products from the database
    public static ArrayList<Product> fetchProducts() {
        ArrayList<Product> productList = new ArrayList<>();

        // SQL query to fetch products from the "Productss" table
        String query = "SELECT product_name, stock_quantity, price FROM Productss";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement(); // createStatement() is a method from the Connection class. It creates a Statement object
                 ResultSet rs = stmt.executeQuery(query)) {

                // Fetch each product from the result set and add to the list
                while (rs.next()) { //row of data
                    String name = rs.getString("product_name");
                    int stock = rs.getInt("stock_quantity");
                    double price = rs.getDouble("price");

                    productList.add(new Product(name, stock, price));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // Handle JDBC driver loading issues
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL issues
        }

        return productList;  // Return the list of products
    }
  
}

    



    // Subtract 1 from the stock quantity when the customer pays

  
