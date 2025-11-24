import java.util.UUID;

// Represents a product in inventory (Encapsulation)
public class Item {
    private String itemID;
    private String name;
    private double price;
    private int stockQuantity;

    // Constructor
    public Item(String name, double price, int stockQuantity) {
        this.itemID = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Constructor used for loading from file (includes ID)
    public Item(String itemID, String name, double price, int stockQuantity) {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    public void setPrice(double price) { this.price = price; }
    public void addStock(int quantity) { this.stockQuantity += quantity; }
    public void reduceStock(int quantity) { this.stockQuantity -= quantity; }

    // Serialization method for file saving
    public String toFileString() {
        return itemID + "," + name + "," + price + "," + stockQuantity;
    }

    @Override
    public String toString() {
        return String.format("| %s | %-20s | $%.2f | Stock: %d",
            itemID, name, price, stockQuantity);
    }
}
