package Cashier;

import java.sql.*;
import java.util.ArrayList;

public class ProductQuery {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT product_name, price, category FROM products";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");

                products.add(new Product(name, category, price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    
    public static ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT product_name, price, category FROM products WHERE category = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                String cat = rs.getString("category");

                products.add(new Product(name, cat, price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    
    
    public int createNewBill(double totalAmount) {
        
            String query = "INSERT INTO Bills (total_amount) VALUES (?)";
            int billId = -1;  // Variable to store the generated bill_id
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDouble(1, totalAmount);  // Set total amount for the bill
                stmt.executeUpdate();

                // Retrieve the generated bill_id
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    billId = generatedKeys.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return billId;
}

    
    
    public static void addProductToBillItems(int billId, Product product, int quantity) {
    String query = "INSERT INTO Bill_Items (bill_id, product_id, quantity, unit_price, line_total) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        double unitPrice = product.getPrice();
        double lineTotal = unitPrice * quantity;
        
        stmt.setInt(1, billId);           
        stmt.setInt(2, product.getId()); 
        stmt.setInt(3, quantity);         
        stmt.setDouble(4, unitPrice);     
        stmt.setDouble(5, lineTotal);     

        stmt.executeUpdate(); 
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}
    public int getProductIdByName(String productName) {
    int productId = -1;
    String query = "SELECT product_id FROM Products WHERE product_name = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, productName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            productId = rs.getInt("product_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productId;
    }
    
    public String getCategoryByName(String productName) {
    String category = null;
    String query = "SELECT category FROM Products WHERE product_name = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, productName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            category = rs.getString("category");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return category;
}
    public static void updateProductStock(int productId, int quantitySold) {
    
    String query = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setInt(1, quantitySold); 
        stmt.setInt(2, productId);     
        stmt.executeUpdate(); 
    } catch (SQLException e) {
        e.printStackTrace();
    }
}




}


