package jug.istanbul.author.domain.service;

import jug.istanbul.author.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorClassificationService Tests - Pure Domain Functions")
class AuthorClassificationServiceTest {
    
    @ParameterizedTest
    @CsvSource({
        "0, ASPIRING",
        "1, BEGINNER",
        "3, BEGINNER",
        "5, BEGINNER",
        "6, DEVELOPING",
        "8, DEVELOPING", 
        "10, DEVELOPING",
        "11, ESTABLISHED",
        "50, ESTABLISHED",
        "100, ESTABLISHED",
        "101, PROLIFIC",
        "500, PROLIFIC"
    })
    @DisplayName("Should classify productivity levels correctly")
    void shouldClassifyProductivityLevels(int books, ProductivityLevel expected) {
        // Given
        BookCount bookCount = new BookCount(books);
        
        // When
        ProductivityLevel actual = AuthorClassificationService.classifyProductivity(bookCount);
        
        // Then
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Should identify prolific authors correctly")
    void shouldIdentifyProlificAuthors() {
        // Given
        BookCount nonprolific = new BookCount(100);
        BookCount prolific = new BookCount(101);
        
        // When & Then
        assertFalse(AuthorClassificationService.isProlific(nonprolific));
        assertTrue(AuthorClassificationService.isProlific(prolific));
    }
    
    @Test
    @DisplayName("Should determine award eligibility correctly")
    void shouldDetermineAwardEligibility() {
        // Given
        AuthorEntity prolificAuthor = AuthorEntity.create("Isaac", "Asimov").publishBooks(150);
        AuthorEntity nonprolificAuthor = AuthorEntity.create("Jane", "Smith").publishBooks(50);
        
        // When & Then
        assertTrue(AuthorClassificationService.canReceiveAward(prolificAuthor),
            "Prolific author with valid name should be eligible for award");
        assertFalse(AuthorClassificationService.canReceiveAward(nonprolificAuthor),
            "Non-prolific author should not be eligible for award");
    }
    
    @Test
    @DisplayName("Should demonstrate pure function properties")
    void shouldDemonstratePureFunctionProperties() {
        // Given
        BookCount bookCount = new BookCount(150);
        
        // When - Call same function multiple times
        ProductivityLevel result1 = AuthorClassificationService.classifyProductivity(bookCount);
        ProductivityLevel result2 = AuthorClassificationService.classifyProductivity(bookCount);
        ProductivityLevel result3 = AuthorClassificationService.classifyProductivity(bookCount);
        
        // Then - Pure function always returns same result for same input
        assertEquals(ProductivityLevel.PROLIFIC, result1);
        assertEquals(result1, result2);
        assertEquals(result2, result3);
        
        // And input is unchanged (immutable)
        assertEquals(150, bookCount.value());
    }
    
    @Test
    @DisplayName("Should work with different BookCount instances with same value")
    void shouldWorkWithDifferentInstances() {
        // Given - Different instances with same value
        BookCount bookCount1 = new BookCount(75);
        BookCount bookCount2 = new BookCount(75);
        
        // When
        ProductivityLevel level1 = AuthorClassificationService.classifyProductivity(bookCount1);
        ProductivityLevel level2 = AuthorClassificationService.classifyProductivity(bookCount2);
        
        // Then - Pure function depends only on value, not object identity
        assertEquals(ProductivityLevel.ESTABLISHED, level1);
        assertEquals(level1, level2);
    }
}
