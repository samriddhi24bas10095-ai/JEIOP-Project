import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Persistence Layer: Handles saving and loading data
public class FileHandler {
    private static final String ITEM_FILE = "inventory.csv";
    private static final String ORDER_FILE = "orders.csv";

    // --- Item Persistence ---
    public static void saveItems(List<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEM_FILE))) {
            for (Item item : items) {
                writer.write(item.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Could not save inventory data."); // Error Handling
        }
    }

    public static List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        File file = new File(ITEM_FILE);
        if (!file.exists()) { return items; }

        // FIX: This line is now correct, fixing the 'new new' and structure errors.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    items.add(new Item(
                        parts[0], parts[1], 
                        Double.parseDouble(parts[2]), 
                        Integer.parseInt(parts[3])
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) { 
            System.err.println("[ERROR] Error loading inventory data. Starting with empty inventory.");
            return new ArrayList<>();
        }
        return items;
    }

    // --- Order Persistence ---
    public static void saveOrders(List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE))) {
            for (Order order : orders) {
                writer.write(order.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Could not save order data.");
        }
    }

    public static List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDER_FILE);
        if (!file.exists()) { return orders; }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    orders.add(new Order(
                        parts[0], parts[1], parts[2], 
                        Integer.parseInt(parts[3]), 
                        Double.parseDouble(parts[4]), 
                        Double.parseDouble(parts[5]), 
                        parts[6]
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("[ERROR] Error loading order data. Starting with empty order history.");
            return new ArrayList<>();
        }
        return orders;
    }
}
