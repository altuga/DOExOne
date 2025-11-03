package jug.istanbul.principle2;

import java.util.List;

// DO Principle #2: Simple data structure with composition
// Order contains simple data structures
public record Order(
    String orderId,
    Customer customer,
    List<Product> products
) {
    // Make list immutable
    public Order {
        products = List.copyOf(products);
    }
}
