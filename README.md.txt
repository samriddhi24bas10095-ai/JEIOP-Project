# Java E-commerce Inventory and Order Processor (JEIOP)

## Overview
JEIOP is a console application that demonstrates robust Object-Oriented Design in a simplified e-commerce setting, managing product inventory and sales transactions.

## Features
* **Inventory Management (CRUD):** Manage all product attributes (Name, Price, Stock).
* **Order Creation:** Simulates customer purchases, automatically reducing stock quantity.
* **Sales Reporting:** Generates a financial report detailing total revenue and current inventory value.

## Non-Functional Requirements
* **Reliability:** Uses File I/O for data persistence (saves on exit, loads on startup).
* **Error Handling:** Implements exception handling for all numerical inputs to ensure stability.

## Technologies/Tools Used
* Programming Language: Java SE 11+
* Version Control: Git (submission via manual upload)

## Steps to Install & Run the Project
1.  Ensure you have the 'src' folder with all 5 Java files in the project root.
2.  **Compile the Source Code:** Navigate to the root folder in your terminal and compile (Windows command):
    ```bash
    javac src/Main.java src/Item.java src/Order.java src/InventoryManager.java src/FileHandler.java
    ```
3.  **Run the Application:** Execute the main class from the root folder:
    ```bash
    java Main
    ```