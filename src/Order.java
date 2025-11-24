import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Represents a single completed customer order
public class Order {
    private String orderID;
    private String itemID;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double totalCost;
    private String dateTime;

    // Constructor
    public Order(Item item, int quantity) {
        this.orderID = "ORD-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        this.itemID = item.getItemID();
        this.itemName = item.getName();
        this.quantity = quantity;
        this.unitPrice = item.getPrice();
        this.totalCost = unitPrice * quantity;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Constructor used for loading from file
    public Order(String orderID, String itemID, String itemName, int quantity, double unitPrice, double totalCost, String dateTime) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalCost = totalCost;
        this.dateTime = dateTime;
    }

    // Getters
    public double getTotalCost() { return totalCost; }
    
    // Serialization method for file saving
    public String toFileString() {
        return orderID + "," + itemID + "," + itemName + "," + quantity + "," + unitPrice + "," + totalCost + "," + dateTime;
    }

    @Override
    public String toString() {
        return String.format("Order ID: %s | Item: %s (x%d) | Total: $%.2f | Date: %s",
            orderID, itemName, quantity, totalCost, dateTime.substring(0, 10));
    }
}
