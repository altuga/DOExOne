package jug.istanbul.author.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookCount Value Object Tests")
class BookCountTest {
    
    @Test
    @DisplayName("Should create valid BookCount with non-negative value")
    void shouldCreateValidBookCount() {
        // Given
        int value = 42;
        
        // When
        BookCount bookCount = new BookCount(value);
        
        // Then
        assertEquals(value, bookCount.value());
    }
    
    @Test
    @DisplayName("Should accept zero books")
    void shouldAcceptZeroBooks() {
        // Given & When
        BookCount bookCount = new BookCount(0);
        
        // Then
        assertEquals(0, bookCount.value());
    }
    
    @Test
    @DisplayName("Should reject negative book count")
    void shouldRejectNegativeBookCount() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new BookCount(-1),
            "Should reject negative book count");
    }
    
    @Test
    @DisplayName("Should increment book count immutably")
    void shouldIncrementImmutably() {
        // Given
        BookCount original = new BookCount(5);
        
        // When
        BookCount incremented = original.increment();
        
        // Then - Original unchanged, new instance returned
        assertEquals(5, original.value());
        assertEquals(6, incremented.value());
    }
    
    @Test
    @DisplayName("Should add books immutably")
    void shouldAddBooksImmutably() {
        // Given
        BookCount original = new BookCount(10);
        
        // When
        BookCount increased = original.add(5);
        
        // Then - Original unchanged, new instance returned
        assertEquals(10, original.value());
        assertEquals(15, increased.value());
    }
    
    @Test
    @DisplayName("Should demonstrate immutability chain operations")
    void shouldDemonstrateImmutabilityChain() {
        // Given
        BookCount start = new BookCount(0);
        
        // When - Chain operations
        BookCount result = start
            .increment()
            .add(10)
            .increment()
            .add(5);
        
        // Then
        assertEquals(0, start.value()); // Original unchanged
        assertEquals(17, result.value()); // 0 + 1 + 10 + 1 + 5
    }
}
