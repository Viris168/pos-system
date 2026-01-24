package Cashier;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String category;

    public Product(int id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
    
    public Product(String name, String category, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
    
    public String getCategory(){
        return category;
    }
}
