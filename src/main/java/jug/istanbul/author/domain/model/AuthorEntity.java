package jug.istanbul.author.domain.model;

// Entity - Author with identity and composed of Value Objects
public record AuthorEntity(AuthorId id, AuthorName name, BookCount bookCount) {
    
    // Factory method for creating new authors
    public static AuthorEntity create(String firstName, String lastName) {
        return new AuthorEntity(
            AuthorId.generate(),
            new AuthorName(firstName, lastName),
            new BookCount(0)
        );
    }
    
    // Business operation - publish a book
    public AuthorEntity publishBook() {
        return new AuthorEntity(id, name, bookCount.increment());
    }
    
    // Business operation - publish multiple books
    public AuthorEntity publishBooks(int count) {
        return new AuthorEntity(id, name, bookCount.add(count));
    }
}
