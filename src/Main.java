import java.util.InputMismatchException;
import java.util.Scanner;

// Presentation Layer: Handles CLI interaction
public class Main {
    private static InventoryManager manager = new InventoryManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("JAVA E-COMMERCE INVENTORY PROCESSOR (JEIOP)");
        System.out.println("=============================================");
        runMenu();
    }

    private static void runMenu() {
        int choice = -1;
        while (choice != 0) {
            displayMenu();
            try {
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: addItem(); break;
                    case 2: manager.displayAllItems(); break;
                    case 3: updateItemStock(); break;
                    case 4: processNewOrder(); break;
                    case 5: manager.generateSalesReport(); break;
                    case 0: 
                        System.out.println("Saving all data and exiting. Goodbye!");
                        // Data is saved automatically in manager methods, but a final save is good practice
                        break; 
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) { // Error Handling [cite: 41]
                System.out.println("\n[ERROR] Invalid input. Please enter a number from the menu.");
                scanner.nextLine(); // Clear the invalid input
                choice = -1; 
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add New Item (CRUD)");
        System.out.println("2. View All Inventory (CRUD)");
        System.out.println("3. Update Item Stock/Price (CRUD)");
        System.out.println("4. Process New Order (Functional Module 2)");
        System.out.println("5. View Sales and Valuation Report (Functional Module 3)");
        System.out.println("0. Exit Application");
    }
    
    // --- Functional Implementation Methods ---

    private static void addItem() {
        try {
            System.out.println("\n--- Add New Item ---");
            System.out.print("Enter Item Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Unit Price: ");
            double price = readDoubleInput();

            System.out.print("Enter Initial Stock Quantity: ");
            int quantity = readIntInput();

            Item newItem = new Item(name, price, quantity);
            manager.addItem(newItem);

        } catch (Exception e) {
            System.out.println("[ERROR] An error occurred during item creation.");
        }
    }

    private static void updateItemStock() {
        manager.displayAllItems();
        System.out.println("\n--- Update Stock/Price ---");
        System.out.print("Enter Item ID to modify: ");
        String id = scanner.nextLine().trim();
        Item item = manager.getItemByID(id);

        if (item == null) {
            System.out.println("Error: Item ID not found.");
            return;
        }

        System.out.println("Modifying: " + item.getName());
        System.out.print("Enter new Stock Quantity (enter current: " + item.getStockQuantity() + " to keep): ");
        int newStock = readIntInput();
        
        System.out.print("Enter new Price (enter current: $" + item.getPrice() + " to keep): ");
        double newPrice = readDoubleInput();

        if (newStock >= 0) {
            // Simple way to set stock after validation
            int difference = newStock - item.getStockQuantity();
            if (difference > 0) {
                item.addStock(difference);
            } else if (difference < 0) {
                item.reduceStock(-difference);
            }
        }
        item.setPrice(newPrice);
        System.out.println("Item updated successfully.");
    }

    private static void processNewOrder() {
        manager.displayAllItems();
        System.out.println("\n--- Process New Order ---");
        System.out.print("Enter Item ID for purchase: ");
        String id = scanner.nextLine().trim();
        Item item = manager.getItemByID(id);

        if (item == null) {
            System.out.println("Error: Item ID not found.");
            return;
        }

        System.out.printf("Item: %s ($%.2f). Stock: %d%n", item.getName(), item.getPrice(), item.getStockQuantity());
        System.out.print("Enter Quantity to purchase: ");
        int quantity = readIntInput();
        
        if (quantity > 0) {
            manager.processOrder(id, quantity);
        } else {
            System.out.println("Quantity must be greater than zero.");
        }
    }
    
    // Utility for robust Double input validation
    private static double readDoubleInput() {
        while (true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value < 0) {
                    System.out.print("Value cannot be negative. Enter again: ");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) { // Error Handling [cite: 41]
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine();
            }
        }
    }

    // Utility for robust Integer input validation
    private static int readIntInput() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value < 0) {
                    System.out.print("Value cannot be negative. Enter again: ");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) { // Error Handling [cite: 41]
                System.out.print("Invalid input. Please enter a whole number: ");
                scanner.nextLine();
            }
        }
    }
}
