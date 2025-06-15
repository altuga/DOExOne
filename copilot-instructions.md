# Copilot Instructions

## Code Style Guidelines

### Switch Expressions
Use `switch` expressions for concise and readable conditional logic. Prefer the modern switch expression syntax over traditional switch statements when possible.

**Good Examples:**
```java
// Switch expression with return value
String dayType = switch (dayOfWeek) {
    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
    case SATURDAY, SUNDAY -> "Weekend";
};

// Switch expression with complex logic
int processOrder = switch (orderStatus) {
    case PENDING -> {
        logOrder(order);
        yield 1;
    }
    case PROCESSING -> 2;
    case COMPLETED -> 3;
    case CANCELLED -> 0;
};

// Switch expression for enum handling
double calculateDiscount = switch (customerType) {
    case REGULAR -> 0.05;
    case PREMIUM -> 0.10;
    case VIP -> 0.15;
};
```

**Avoid:**
```java
// Traditional switch statement (use only when necessary)
String dayType;
switch (dayOfWeek) {
    case MONDAY:
    case TUESDAY:
    case WEDNESDAY:
    case THURSDAY:
    case FRIDAY:
        dayType = "Weekday";
        break;
    case SATURDAY:
    case SUNDAY:
        dayType = "Weekend";
        break;
    default:
        dayType = "Unknown";
}
```

## Data-Oriented Programming Principles

### 1. **Separate Data from Behavior**
Keep data structures simple and separate from the operations that manipulate them.

```java
// Data structure - focus on data representation
public record Customer(String name, String email, CustomerType type, List<Order> orders) {}

// Behavior - separate service classes
public class CustomerService {
    public double calculateTotalSpent(Customer customer) {
        return customer.orders().stream()
            .mapToDouble(Order::amount)
            .sum();
    }
}
```

### 2. **Use Records for Immutable Data**
Prefer records for simple data containers to reduce boilerplate and ensure immutability.

```java
// Instead of traditional class with getters/setters
public record Product(String id, String name, double price, Category category) {}

// For more complex data with validation
public record OrderItem(String productId, int quantity, double unitPrice) {
    public OrderItem {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (unitPrice < 0) throw new IllegalArgumentException("Price cannot be negative");
    }
    
    public double totalPrice() {
        return quantity * unitPrice;
    }
}
```

### 3. **Favor Composition Over Inheritance**
Build complex behavior by composing simple data structures and functions.

```java
// Composition-based approach
public record Address(String street, String city, String zipCode) {}
public record Person(String name, String email, Address address) {}

// Service layer operates on composed data
public class AddressValidator {
    public boolean isValid(Address address) {
        return address.zipCode().matches("\\d{5}") && 
               !address.city().isBlank();
    }
}
```

### 4. **Use Sealed Classes for Controlled Hierarchies**
When you need polymorphism, use sealed classes to control the hierarchy.

```java
public sealed interface PaymentMethod 
    permits CreditCard, PayPal, BankTransfer {
}

public record CreditCard(String number, String expiry) implements PaymentMethod {}
public record PayPal(String email) implements PaymentMethod {}
public record BankTransfer(String accountNumber, String routingNumber) implements PaymentMethod {}

// Pattern matching with switch expressions
public double calculateFee(PaymentMethod payment) {
    return switch (payment) {
        case CreditCard(var number, var expiry) -> 2.9;
        case PayPal(var email) -> 3.5;
        case BankTransfer(var account, var routing) -> 1.0;
    };
}
```

### 5. **Transform Data with Streams**
Use functional programming approaches to transform data rather than mutating it.

```java
// Transform data through pipelines
public List<CustomerSummary> getTopCustomers(List<Customer> customers) {
    return customers.stream()
        .filter(customer -> customer.orders().size() > 5)
        .map(customer -> new CustomerSummary(
            customer.name(),
            customer.orders().size(),
            calculateTotalSpent(customer)
        ))
        .sorted(Comparator.comparing(CustomerSummary::totalSpent).reversed())
        .limit(10)
        .toList();
}
```

### 6. **Use Pattern Matching**
Leverage pattern matching for cleaner conditional logic with data structures.

```java
// Pattern matching with instanceof
public String formatShape(Shape shape) {
    return switch (shape) {
        case Circle(var radius) -> 
            String.format("Circle with radius %.2f", radius);
        case Rectangle(var width, var height) -> 
            String.format("Rectangle %.2f x %.2f", width, height);
        case Square(var side) -> 
            String.format("Square with side %.2f", side);
    };
}
```

### 7. **Keep Functions Pure**
Write functions that don't have side effects and always return the same output for the same input.

```java
// Pure function - no side effects
public double calculateTax(double amount, double rate) {
    return amount * rate;
}

// Pure function with immutable operations
public List<Product> filterByCategory(List<Product> products, Category category) {
    return products.stream()
        .filter(product -> product.category() == category)
        .toList(); // Returns new list, doesn't modify original
}
```

### 8. **Use Optional for Nullable Values**
Handle null values explicitly with Optional rather than returning null.

```java
public Optional<Customer> findCustomerByEmail(String email) {
    return customers.stream()
        .filter(customer -> customer.email().equals(email))
        .findFirst();
}

// Chain operations safely
public String getCustomerCity(String email) {
    return findCustomerByEmail(email)
        .map(Customer::address)
        .map(Address::city)
        .orElse("Unknown");
}
```

## Key Principles Summary

1. **Data First**: Design your data structures before writing behavior
2. **Immutability**: Prefer immutable data structures (records, final fields)
3. **Transparency**: Make data transformations explicit and traceable
4. **Composition**: Build complexity through composition, not inheritance
5. **Functional Style**: Use streams, pure functions, and avoid side effects
6. **Type Safety**: Leverage the type system to prevent errors at compile time
7. **Pattern Matching**: Use modern Java features for cleaner conditional logic

## Modern Java Features to Embrace

- **Records** for data classes
- **Sealed classes** for controlled polymorphism
- **Switch expressions** for concise conditionals
- **Pattern matching** for destructuring
- **Text blocks** for multi-line strings
- **Stream API** for data processing
- **Optional** for null safety
- **var** for local variable type inference (when it improves readability)
