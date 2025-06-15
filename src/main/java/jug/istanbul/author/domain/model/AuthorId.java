package jug.istanbul.author.domain.model;

import java.util.UUID;

// Value Object - Strong typed ID for Author entity
public record AuthorId(String value) {
    public AuthorId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Author ID cannot be empty");
        }
    }
    
    public static AuthorId generate() {
        return new AuthorId(UUID.randomUUID().toString());
    }
    
    public static AuthorId of(String value) {
        return new AuthorId(value);
    }
}
