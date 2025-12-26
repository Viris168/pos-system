
package pos.system;

public class Product {
    private String name;
    private int stockQuantity;
    private double price;

    public Product(String name, int stockQuantity, double price) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void reduceStock() {
        this.stockQuantity--;
    }

    // Optional: Implement toString() for easy displaying of products
    @Override
    public String toString() {
        return name + " - " + stockQuantity + " - " + price;
    }
}
