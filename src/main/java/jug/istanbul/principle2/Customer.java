package jug.istanbul.principle2;

// DO Principle #2: Simple data structure
// Customer as simple data - no behavior
public record Customer(String name, String email, int age) {
    // Validation in compact constructor
    public Customer {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
