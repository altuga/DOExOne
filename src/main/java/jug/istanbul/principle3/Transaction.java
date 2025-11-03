package jug.istanbul.principle3;

import java.time.LocalDateTime;
import java.util.List;

// DO Principle #3: Immutable transaction record
public record Transaction(
    String id,
    String accountNumber,
    double amount,
    TransactionType type,
    LocalDateTime timestamp
) {
    // No setters - once created, cannot be changed!
}
