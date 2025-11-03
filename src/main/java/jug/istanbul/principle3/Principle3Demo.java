package jug.istanbul.principle3;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Demo: DO Principle #3 - Data is Immutable
public class Principle3Demo {
    public static void main(String[] args) {
        System.out.println("=== DO Principle #3 Demo ===");
        System.out.println("Immutable Data - Once Created, Never Changed\n");
        
        // 1. Create immutable data
        BankAccount account = new BankAccount("ACC-001", "Alice", 1000.0);
        System.out.println("Original account: " + account.owner() + 
                         " - Balance: $" + account.balance());
        
        // 2. ❌ Cannot do this - no setters!
        // account.setBalance(2000.0);  // COMPILE ERROR - no setter exists!
        
        // 3. ✅ Operations return NEW objects
        System.out.println("\n--- Deposit $500 ---");
        BankAccount afterDeposit = account.deposit(500.0);
        
        System.out.println("Original account: $" + account.balance());
        System.out.println("After deposit: $" + afterDeposit.balance());
        System.out.println("✅ Original unchanged! (Immutability)");
        
        // 4. ✅ Chain operations - each returns new object
        System.out.println("\n--- Chain operations ---");
        BankAccount finalAccount = account
            .deposit(200.0)   // Returns new account
            .withdraw(50.0)   // Returns another new account
            .deposit(100.0);  // Returns yet another new account
        
        System.out.println("Original: $" + account.balance());
        System.out.println("After chain: $" + finalAccount.balance());
        System.out.println("✅ Original still unchanged!");
        
        // 5. Immutable transaction history
        System.out.println("\n--- Transaction History ---");
        AccountHistory history = new AccountHistory(account, List.of());
        
        Transaction t1 = new Transaction(
            UUID.randomUUID().toString(),
            account.accountNumber(),
            500.0,
            TransactionType.DEPOSIT,
            LocalDateTime.now()
        );
        
        // Adding transaction returns NEW history
        AccountHistory newHistory = history.addTransaction(t1);
        
        System.out.println("Original history transactions: " + history.transactions().size());
        System.out.println("New history transactions: " + newHistory.transactions().size());
        System.out.println("✅ Original history unchanged!");
        
        // 6. Why immutability matters
        System.out.println("\n=== Why Immutability? ===");
        System.out.println("✅ Thread-safe - no race conditions");
        System.out.println("✅ Predictable - data never changes unexpectedly");
        System.out.println("✅ Easy to test - no hidden state changes");
        System.out.println("✅ Time-travel debugging - keep all versions");
        System.out.println("✅ Caching-friendly - safe to share references");
        
        // 7. Demonstrate reference safety
        System.out.println("\n--- Reference Safety ---");
        BankAccount shared = new BankAccount("ACC-002", "Bob", 500.0);
        BankAccount ref1 = shared;
        BankAccount ref2 = shared.deposit(100.0);  // Different object
        
        System.out.println("Shared: $" + shared.balance());
        System.out.println("Ref1: $" + ref1.balance());
        System.out.println("Ref2: $" + ref2.balance());
        System.out.println("✅ Safe to share - immutability guarantees no surprises!");
    }
}
