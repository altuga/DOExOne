package jug.istanbul.library;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// DO: Separate behavior - Loan operations
public class LoanOperations {
    private LoanOperations() {} // Utility class
    
    // Get active loans for a member
    public static List<Loan> getActiveLoans(List<Loan> loans, String memberId) {
        return loans.stream()
            .filter(loan -> loan.memberId().equals(memberId))
            .filter(Loan::isActive)
            .collect(Collectors.toList());
    }
    
    // Get overdue loans for a member
    public static List<Loan> getOverdueLoans(List<Loan> loans, String memberId) {
        return loans.stream()
            .filter(loan -> loan.memberId().equals(memberId))
            .filter(Loan::isOverdue)
            .collect(Collectors.toList());
    }
    
    // Check if member has overdue books
    public static boolean hasOverdueBooks(List<Loan> loans, String memberId) {
        return loans.stream()
            .filter(loan -> loan.memberId().equals(memberId))
            .anyMatch(Loan::isOverdue);
    }
    
    // Get all books borrowed by a member (with book details)
    public static List<BookLoanInfo> getBorrowedBooks(
        List<Loan> loans,
        List<BookCopy> copies,
        List<Book> books,
        String memberId
    ) {
        return getActiveLoans(loans, memberId).stream()
            .map(loan -> {
                BookCopy copy = copies.stream()
                    .filter(c -> c.copyId().equals(loan.copyId()))
                    .findFirst()
                    .orElse(null);
                    
                if (copy == null) return null;
                
                Book book = books.stream()
                    .filter(b -> b.bookId().equals(copy.bookId()))
                    .findFirst()
                    .orElse(null);
                    
                return new BookLoanInfo(book, copy, loan);
            })
            .filter(info -> info != null)
            .collect(Collectors.toList());
    }
    
    // Helper record for loan information
    public record BookLoanInfo(Book book, BookCopy copy, Loan loan) {}
}
