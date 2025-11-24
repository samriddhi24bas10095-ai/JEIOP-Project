import java.util.ArrayList;
import java.util.List;

// Business Logic Layer: Manages the Item and Order collections
public class InventoryManager {
    private List<Item> inventory; // Data Structure 1: ArrayList for items
    private List<Order> salesHistory; // Data Structure 2: ArrayList for orders

    public InventoryManager() {
        this.inventory = FileHandler.loadItems();
        this.salesHistory = FileHandler.loadOrders();
    }

    // --- Functional Module 1: Inventory CRUD ---
    
    // CREATE
    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Item added: " + item.getName());
        saveAllData();
    }

    // READ (Find)
    public Item getItemByID(String id) {
        for (Item item : inventory) {
            if (item.getItemID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // READ (All)
    public void displayAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- CURRENT INVENTORY (" + inventory.size() + " Total Items) ---");
        System.out.println("| ID    | Name                 | Price   | Stock  ");
        System.out.println("-------------------------------------------------");
        for (Item item : inventory) {
            System.out.println(item);
        }
        System.out.println("-------------------------------------------------");
    }

    // DELETE
    public boolean deleteItem(String id) {
        Item item = getItemByID(id);
        if (item != null) {
            inventory.remove(item);
            System.out.println("Item " + item.getName() + " deleted.");
            saveAllData();
            return true;
        }
        return false;
    }

    // --- Functional Module 2: Order Processing ---
    public boolean processOrder(String itemID, int quantity) {
        Item item = getItemByID(itemID);
        if (item == null) {
            System.out.println("Error: Item ID not found.");
            return false;
        }
        if (item.getStockQuantity() < quantity) {
            System.out.println("Error: Insufficient stock. Available: " + item.getStockQuantity());
            return false;
        }

        // 1. Create Order
        Order newOrder = new Order(item, quantity);
        
        // 2. Update Inventory
        item.reduceStock(quantity);
        
        // 3. Record Order
        salesHistory.add(newOrder);
        
        System.out.printf("Order successful! %d x %s processed. Total: $%.2f%n", 
            quantity, item.getName(), newOrder.getTotalCost());
        
        saveAllData();
        return true;
    }

    // --- Functional Module 3: Sales Reporting ---
    public void generateSalesReport() {
        System.out.println("\n--- SALES AND VALUATION REPORT ---");
        
        // 1. Total Revenue
        double totalRevenue = salesHistory.stream().mapToDouble(Order::getTotalCost).sum();
        System.out.printf("Total Revenue from %d Orders: $%.2f%n", salesHistory.size(), totalRevenue);

        // 2. Inventory Valuation
        double inventoryValue = inventory.stream()
            .mapToDouble(item -> item.getPrice() * item.getStockQuantity())
            .sum();
        System.out.printf("Current Total Inventory Value: $%.2f%n", inventoryValue);
        
        System.out.println("----------------------------------");
    }

    // Utility to save both lists
    private void saveAllData() {
        FileHandler.saveItems(inventory);
        FileHandler.saveOrders(salesHistory);
    }
}
