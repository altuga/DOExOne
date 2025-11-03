package jug.istanbul.principle2;

// DO Principle #2: Behavior separated from data
// All operations as pure functions
public class OrderCalculations {
    private OrderCalculations() {} // Utility class
    
    // Calculate total price
    public static double calculateTotal(Order order) {
        return order.products().stream()
            .mapToDouble(product -> product.price() * product.quantity())
            .sum();
    }
    
    // Calculate total items
    public static int calculateItemCount(Order order) {
        return order.products().stream()
            .mapToInt(Product::quantity)
            .sum();
    }
    
    // Check if order qualifies for discount
    public static boolean qualifiesForDiscount(Order order) {
        return calculateTotal(order) > 100.0;
    }
    
    // Calculate discount amount
    public static double calculateDiscount(Order order) {
        if (qualifiesForDiscount(order)) {
            return calculateTotal(order) * 0.10; // 10% discount
        }
        return 0.0;
    }
}
