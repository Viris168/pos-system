package Cashier;

import java.sql.*;
import java.util.ArrayList;

public class ProductQuery {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT name, price, category FROM products";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
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

        String sql = "SELECT name, price, category FROM products WHERE category = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String cat = rs.getString("category");

                products.add(new Product(name, cat, price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

}


