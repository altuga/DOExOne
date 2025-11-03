package jug.istanbul.principle2;

// DO Principle #2: Simple data structure
// Just data - no complex methods
public record Product(String name, double price, int quantity) {
    // Validation rule
    public Product {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
