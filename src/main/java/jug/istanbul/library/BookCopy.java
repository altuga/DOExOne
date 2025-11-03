package jug.istanbul.library;

// DO: Simple immutable data - Book copy (multiple copies of same book)
public record BookCopy(
    String copyId,
    String bookId,
    BookCopyStatus status
) {
    public static BookCopy create(String copyId, String bookId) {
        return new BookCopy(copyId, bookId, BookCopyStatus.AVAILABLE);
    }
    
    // Immutable operations
    public BookCopy borrow() {
        if (status == BookCopyStatus.BORROWED) {
            throw new IllegalStateException("Book copy is already borrowed");
        }
        return new BookCopy(copyId, bookId, BookCopyStatus.BORROWED);
    }
    
    public BookCopy returnBook() {
        if (status == BookCopyStatus.AVAILABLE) {
            throw new IllegalStateException("Book copy is not borrowed");
        }
        return new BookCopy(copyId, bookId, BookCopyStatus.AVAILABLE);
    }
}
