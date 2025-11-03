package jug.istanbul.library;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// DO: Library service using functional composition
public class LibraryService {
    private final List<User> users;
    private final List<UserCredentials> credentials;
    private final List<Book> books;
    private final List<BookCopy> bookCopies;
    private final List<Loan> loans;
    
    public LibraryService(
        List<User> users,
        List<UserCredentials> credentials,
        List<Book> books,
        List<BookCopy> bookCopies,
        List<Loan> loans
    ) {
        this.users = new ArrayList<>(users);
        this.credentials = new ArrayList<>(credentials);
        this.books = new ArrayList<>(books);
        this.bookCopies = new ArrayList<>(bookCopies);
        this.loans = new ArrayList<>(loans);
    }
    
    // Authentication
    public Optional<User> login(String email, String password) {
        return UserOperations.authenticate(users, credentials, email, password);
    }
    
    // Search operations (available to all users)
    public List<Book> searchBooksByTitle(String title) {
        return BookSearchOperations.searchByTitle(books, title);
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        return BookSearchOperations.searchByAuthor(books, author);
    }
    
    // Member operations
    public Optional<Loan> borrowBook(User member, String bookId) {
        // Check if user can borrow
        if (!UserOperations.canBorrow(member)) {
            return Optional.empty();
        }
        
        // Check for overdue books
        if (LoanOperations.hasOverdueBooks(loans, member.userId())) {
            return Optional.empty();
        }
        
        // Find available copy
        List<BookCopy> availableCopies = BookSearchOperations.findAvailableCopies(bookCopies, bookId);
        if (availableCopies.isEmpty()) {
            return Optional.empty();
        }
        
        // Borrow first available copy
        BookCopy copy = availableCopies.get(0);
        BookCopy borrowedCopy = copy.borrow();
        
        // Update copy in list
        bookCopies.replaceAll(c -> c.copyId().equals(copy.copyId()) ? borrowedCopy : c);
        
        // Create loan
        Loan loan = Loan.create(
            UUID.randomUUID().toString(),
            member.userId(),
            copy.copyId(),
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(14)
        );
        loans.add(loan);
        
        return Optional.of(loan);
    }
    
    // Librarian operations
    public User blockMember(User librarian, String memberId) {
        if (!UserOperations.isLibrarian(librarian)) {
            throw new IllegalArgumentException("Only librarians can block members");
        }
        
        User member = users.stream()
            .filter(u -> u.userId().equals(memberId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        User blockedMember = member.block();
        users.replaceAll(u -> u.userId().equals(memberId) ? blockedMember : u);
        
        return blockedMember;
    }
    
    public User unblockMember(User librarian, String memberId) {
        if (!UserOperations.isLibrarian(librarian)) {
            throw new IllegalArgumentException("Only librarians can unblock members");
        }
        
        User member = users.stream()
            .filter(u -> u.userId().equals(memberId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        User unblockedMember = member.unblock();
        users.replaceAll(u -> u.userId().equals(memberId) ? unblockedMember : u);
        
        return unblockedMember;
    }
    
    public List<LoanOperations.BookLoanInfo> listBorrowedBooks(User librarian, String memberId) {
        if (!UserOperations.isLibrarian(librarian)) {
            throw new IllegalArgumentException("Only librarians can list borrowed books");
        }
        
        return LoanOperations.getBorrowedBooks(loans, bookCopies, books, memberId);
    }
    
    // Getters for immutable views
    public List<User> getUsers() {
        return List.copyOf(users);
    }
    
    public List<Book> getBooks() {
        return List.copyOf(books);
    }
}
