package jug.istanbul.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Demo: Library Management System in Data-Oriented Style
public class LibraryDemo {
    public static void main(String[] args) {
        System.out.println("=== Library Management System (Data-Oriented) ===\n");
        
        // Setup: Create immutable data
        List<User> users = new ArrayList<>();
        List<UserCredentials> credentials = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        List<BookCopy> bookCopies = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        
        // Add librarian
        User librarian = User.createLibrarian("L001", "Alice Admin", "alice@library.com");
        users.add(librarian);
        credentials.add(new UserCredentials("alice@library.com", "password123"));
        
        // Add members
        User member1 = User.createMember("M001", "Bob Reader", "bob@email.com");
        User member2 = User.createMember("M002", "Carol Student", "carol@email.com");
        users.add(member1);
        users.add(member2);
        credentials.add(new UserCredentials("bob@email.com", "password123"));
        credentials.add(new UserCredentials("carol@email.com", "password123"));
        
        // Add books
        Book book1 = new Book("B001", "Clean Code", "Robert Martin", "978-0132350884");
        Book book2 = new Book("B002", "Design Patterns", "Gang of Four", "978-0201633610");
        Book book3 = new Book("B003", "Domain-Driven Design", "Eric Evans", "978-0321125217");
        books.add(book1);
        books.add(book2);
        books.add(book3);
        
        // Add multiple copies of books
        bookCopies.add(BookCopy.create("C001", "B001"));
        bookCopies.add(BookCopy.create("C002", "B001")); // Second copy of Clean Code
        bookCopies.add(BookCopy.create("C003", "B002"));
        bookCopies.add(BookCopy.create("C004", "B003"));
        
        // Create service
        LibraryService library = new LibraryService(users, credentials, books, bookCopies, loans);
        
        // Use case 1: Login
        System.out.println("1. User Login");
        Optional<User> loggedInMember = library.login("bob@email.com", "password123");
        Optional<User> loggedInLibrarian = library.login("alice@library.com", "password123");
        
        System.out.println("Member login: " + loggedInMember.map(User::name).orElse("Failed"));
        System.out.println("Librarian login: " + loggedInLibrarian.map(User::name).orElse("Failed"));
        
        // Use case 2: Search books
        System.out.println("\n2. Search Books");
        List<Book> cleanBooks = library.searchBooksByTitle("Clean");
        System.out.println("Search 'Clean': " + cleanBooks.size() + " results");
        cleanBooks.forEach(b -> System.out.println("  - " + b.title() + " by " + b.author()));
        
        List<Book> martinBooks = library.searchBooksByAuthor("Martin");
        System.out.println("Search author 'Martin': " + martinBooks.size() + " results");
        
        // Use case 3: Member borrows book
        System.out.println("\n3. Member Borrows Book");
        if (loggedInMember.isPresent()) {
            Optional<Loan> loan = library.borrowBook(loggedInMember.get(), "B001");
            if (loan.isPresent()) {
                System.out.println("✅ Book borrowed successfully!");
                System.out.println("   Loan ID: " + loan.get().loanId());
                System.out.println("   Due date: " + loan.get().dueDate());
            }
        }
        
        // Use case 4: Librarian lists borrowed books
        System.out.println("\n4. Librarian Lists Member's Books");
        if (loggedInLibrarian.isPresent()) {
            List<LoanOperations.BookLoanInfo> borrowedBooks = 
                library.listBorrowedBooks(loggedInLibrarian.get(), member1.userId());
            
            System.out.println(member1.name() + " has borrowed " + borrowedBooks.size() + " book(s):");
            borrowedBooks.forEach(info -> 
                System.out.println("  - " + info.book().title() + 
                                 " (Due: " + info.loan().dueDate() + ")")
            );
        }
        
        // Use case 5: Librarian blocks member
        System.out.println("\n5. Librarian Blocks Member");
        if (loggedInLibrarian.isPresent()) {
            User blockedMember = library.blockMember(loggedInLibrarian.get(), member2.userId());
            System.out.println("Member " + blockedMember.name() + " status: " + blockedMember.status());
            
            // Try to borrow while blocked
            Optional<Loan> failedLoan = library.borrowBook(blockedMember, "B002");
            System.out.println("Blocked member can borrow: " + failedLoan.isPresent());
        }
        
        // Use case 6: Librarian unblocks member
        System.out.println("\n6. Librarian Unblocks Member");
        if (loggedInLibrarian.isPresent()) {
            User unblockedMember = library.unblockMember(loggedInLibrarian.get(), member2.userId());
            System.out.println("Member " + unblockedMember.name() + " status: " + unblockedMember.status());
        }
        
        // Use case 7: Multiple copies of same book
        System.out.println("\n7. Multiple Copies Available");
        int availableCopies = BookSearchOperations.countAvailableCopies(
            library.getBooks().stream()
                .flatMap(b -> bookCopies.stream())
                .toList(),
            "B001"
        );
        System.out.println("'Clean Code' has " + (availableCopies + 1) + " copies (1 borrowed, " + availableCopies + " available)");
        
        System.out.println("\n✅ Data-Oriented Design Benefits:");
        System.out.println("- All data is immutable (records)");
        System.out.println("- Behavior separated into operations classes");
        System.out.println("- Pure functions for business logic");
        System.out.println("- Easy to test and reason about");
        System.out.println("- Type-safe with Java records");
    }
}
