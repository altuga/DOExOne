package jug.istanbul.library;

// DO: Simple immutable data - Book entity
public record Book(
    String bookId,
    String title,
    String author,
    String isbn
) {
    public Book {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
    }
}
