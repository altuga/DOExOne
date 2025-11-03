package jug.istanbul.principle3;

import java.util.ArrayList;
import java.util.List;

// DO Principle #3: Immutable account history
public record AccountHistory(
    BankAccount account,
    List<Transaction> transactions
) {
    // Make list immutable
    public AccountHistory {
        transactions = List.copyOf(transactions);
    }
    
    // ✅ Adding transaction returns NEW history (immutability)
    public AccountHistory addTransaction(Transaction transaction) {
        var newTransactions = new ArrayList<>(transactions);
        newTransactions.add(transaction);
        return new AccountHistory(account, newTransactions);
    }
    
    // ✅ Updating account returns NEW history
    public AccountHistory withAccount(BankAccount newAccount) {
        return new AccountHistory(newAccount, transactions);
    }
}
