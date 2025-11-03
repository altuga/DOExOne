package jug.istanbul.library;

import java.util.List;
import java.util.stream.Collectors;

// DO: Separate behavior - Book search operations
public class BookSearchOperations {
    private BookSearchOperations() {} // Utility class
    
    // Search books by title
    public static List<Book> searchByTitle(List<Book> books, String title) {
        String searchTerm = title.toLowerCase();
        return books.stream()
            .filter(book -> book.title().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }
    
    // Search books by author
    public static List<Book> searchByAuthor(List<Book> books, String author) {
        String searchTerm = author.toLowerCase();
        return books.stream()
            .filter(book -> book.author().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }
    
    // Find available copies of a book
    public static List<BookCopy> findAvailableCopies(List<BookCopy> copies, String bookId) {
        return copies.stream()
            .filter(copy -> copy.bookId().equals(bookId))
            .filter(copy -> copy.status() == BookCopyStatus.AVAILABLE)
            .collect(Collectors.toList());
    }
    
    // Count available copies
    public static int countAvailableCopies(List<BookCopy> copies, String bookId) {
        return (int) copies.stream()
            .filter(copy -> copy.bookId().equals(bookId))
            .filter(copy -> copy.status() == BookCopyStatus.AVAILABLE)
            .count();
    }
}
