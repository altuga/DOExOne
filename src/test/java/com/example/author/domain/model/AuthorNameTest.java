package com.example.author.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorName Value Object Tests")
class AuthorNameTest {
    
    @Test
    @DisplayName("Should create valid AuthorName with proper names")
    void shouldCreateValidAuthorName() {
        // Given
        String firstName = "Isaac";
        String lastName = "Asimov";
        
        // When
        AuthorName authorName = new AuthorName(firstName, lastName);
        
        // Then
        assertEquals(firstName, authorName.firstName());
        assertEquals(lastName, authorName.lastName());
        assertEquals("Isaac Asimov", authorName.fullName());
    }
    
    @Test
    @DisplayName("Should reject null first name")
    void shouldRejectNullFirstName() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new AuthorName(null, "Asimov"),
            "Should reject null first name");
    }
    
    @Test
    @DisplayName("Should reject empty first name")
    void shouldRejectEmptyFirstName() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new AuthorName("", "Asimov"),
            "Should reject empty first name");
    }
    
    @Test
    @DisplayName("Should reject whitespace-only first name")
    void shouldRejectWhitespaceFirstName() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new AuthorName("   ", "Asimov"),
            "Should reject whitespace-only first name");
    }
    
    @Test
    @DisplayName("Should reject null last name")
    void shouldRejectNullLastName() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new AuthorName("Isaac", null),
            "Should reject null last name");
    }
    
    @Test
    @DisplayName("Should demonstrate value object equality")
    void shouldDemonstrateValueObjectEquality() {
        // Given
        AuthorName name1 = new AuthorName("Isaac", "Asimov");
        AuthorName name2 = new AuthorName("Isaac", "Asimov");
        AuthorName name3 = new AuthorName("Jane", "Smith");
        
        // Then - Value objects with same values are equal
        assertEquals(name1, name2);
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1, name3);
    }
}
