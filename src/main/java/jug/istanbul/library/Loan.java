package jug.istanbul.library;

import java.time.LocalDateTime;

// DO: Simple immutable data - Loan record
public record Loan(
    String loanId,
    String memberId,
    String copyId,
    LocalDateTime borrowedAt,
    LocalDateTime dueDate,
    LocalDateTime returnedAt
) {
    public static Loan create(String loanId, String memberId, String copyId, LocalDateTime borrowedAt, LocalDateTime dueDate) {
        return new Loan(loanId, memberId, copyId, borrowedAt, dueDate, null);
    }
    
    // Check if loan is active
    public boolean isActive() {
        return returnedAt == null;
    }
    
    // Check if overdue
    public boolean isOverdue() {
        return isActive() && LocalDateTime.now().isAfter(dueDate);
    }
    
    // Return book (immutable)
    public Loan returnBook(LocalDateTime returnTime) {
        if (!isActive()) {
            throw new IllegalStateException("Loan already returned");
        }
        return new Loan(loanId, memberId, copyId, borrowedAt, dueDate, returnTime);
    }
}
