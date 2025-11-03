package jug.istanbul.principle3;

// DO Principle #3: Immutable data
// Records are immutable by default - cannot change after creation
public record BankAccount(String accountNumber, String owner, double balance) {
    // Validation
    public BankAccount {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
    
    // ✅ Operations return NEW instances (immutability)
    public BankAccount deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        // Returns NEW account with updated balance
        return new BankAccount(accountNumber, owner, balance + amount);
    }
    
    public BankAccount withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        // Returns NEW account with updated balance
        return new BankAccount(accountNumber, owner, balance - amount);
    }
}
