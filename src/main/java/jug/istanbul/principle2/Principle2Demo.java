package jug.istanbul.principle2;

import java.util.List;

// Demo: DO Principle #2 - Represent data entities with generic data structures
public class Principle2Demo {
    public static void main(String[] args) {
        System.out.println("=== DO Principle #2 Demo ===");
        System.out.println("Simple Data Structures + Separate Operations\n");
        
        // 1. Create simple data
        Customer customer = new Customer("John Doe", "john@example.com", 30);
        System.out.println("Customer: " + customer.name());
        
        // 2. Create more simple data
        Product laptop = new Product("Laptop", 999.99, 1);
        Product mouse = new Product("Mouse", 25.50, 2);
        Product keyboard = new Product("Keyboard", 75.00, 1);
        
        // 3. Compose simple data structures
        Order order = new Order("ORD-001", customer, List.of(laptop, mouse, keyboard));
        
        // 4. Use separate functions to operate on data
        System.out.println("\nOrder ID: " + order.orderId());
        System.out.println("Total items: " + OrderCalculations.calculateItemCount(order));
        System.out.println("Total price: $" + OrderCalculations.calculateTotal(order));
        System.out.println("Qualifies for discount: " + OrderCalculations.qualifiesForDiscount(order));
        System.out.println("Discount amount: $" + OrderCalculations.calculateDiscount(order));
        
        // 5. Data is immutable - transformations create new data
        System.out.println("\n=== Immutability Demo ===");
        Product cheapMouse = new Product(mouse.name(), 15.00, mouse.quantity());
        System.out.println("Original mouse: $" + mouse.price());
        System.out.println("New mouse: $" + cheapMouse.price());
        System.out.println("Original unchanged: $" + mouse.price());
        
        System.out.println("\n✅ Key Points:");
        System.out.println("- Data is simple (records)");
        System.out.println("- Operations are separate (OrderCalculations)");
        System.out.println("- Everything is immutable");
        System.out.println("- Easy to test and understand");
    }
}
