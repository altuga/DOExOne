package jug.istanbul.author.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorEntity Tests - DDD Entity with DOP Implementation")
class AuthorEntityTest {
    
    @Test
    @DisplayName("Should create new author with factory method")
    void shouldCreateNewAuthorWithFactory() {
        // When
        AuthorEntity author = AuthorEntity.create("Isaac", "Asimov");
        
        // Then
        assertNotNull(author.id());
        assertEquals("Isaac", author.name().firstName());
        assertEquals("Asimov", author.name().lastName());
        assertEquals(0, author.bookCount().value());
    }
    
    @Test
    @DisplayName("Should generate unique IDs for different authors")
    void shouldGenerateUniqueIds() {
        // When
        AuthorEntity author1 = AuthorEntity.create("Isaac", "Asimov");
        AuthorEntity author2 = AuthorEntity.create("Jane", "Smith");
        
        // Then
        assertNotEquals(author1.id(), author2.id());
    }
    
    @Test
    @DisplayName("Should publish book immutably")
    void shouldPublishBookImmutably() {
        // Given
        AuthorEntity original = AuthorEntity.create("Isaac", "Asimov");
        
        // When
        AuthorEntity updated = original.publishBook();
        
        // Then - Entity identity preserved, data changed immutably
        assertEquals(original.id(), updated.id()); // Same identity
        assertEquals(original.name(), updated.name()); // Same name
        assertEquals(0, original.bookCount().value()); // Original unchanged
        assertEquals(1, updated.bookCount().value()); // New instance updated
    }
    
    @Test
    @DisplayName("Should publish multiple books immutably")
    void shouldPublishMultipleBooksImmutably() {
        // Given
        AuthorEntity original = AuthorEntity.create("Isaac", "Asimov");
        
        // When
        AuthorEntity updated = original.publishBooks(5);
        
        // Then
        assertEquals(original.id(), updated.id());
        assertEquals(0, original.bookCount().value());
        assertEquals(5, updated.bookCount().value());
    }
    
    @Test
    @DisplayName("Should demonstrate entity equality based on identity")
    void shouldDemonstrateEntityEquality() {
        // Given
        AuthorEntity author1 = AuthorEntity.create("Isaac", "Asimov");
        AuthorEntity author2 = author1.publishBooks(100); // Same ID, different state
        AuthorEntity author3 = AuthorEntity.create("Isaac", "Asimov"); // Same data, different ID
        
        // Then - Entities are equal if they have same ID (identity)
        assertEquals(author1.id(), author2.id()); // Same identity
        assertNotEquals(author1.id(), author3.id()); // Different identity
        
        // Even though data might be different, same ID means same entity
        assertEquals(author1.id(), author2.id());
        assertEquals(author1.name(), author2.name());
        assertNotEquals(author1.bookCount(), author2.bookCount());
    }
    
    @Test
    @DisplayName("Should demonstrate business operations chain")
    void shouldDemonstrateBusinessOperationsChain() {
        // Given
        AuthorEntity author = AuthorEntity.create("Isaac", "Asimov");
        
        // When - Chain business operations
        AuthorEntity prolificAuthor = author
            .publishBook()           // 1 book
            .publishBooks(50)        // 51 books
            .publishBook()           // 52 books
            .publishBooks(100);      // 152 books
        
        // Then
        assertEquals(author.id(), prolificAuthor.id()); // Same entity
        assertEquals(0, author.bookCount().value()); // Original unchanged
        assertEquals(152, prolificAuthor.bookCount().value()); // Final state
    }
}
