package com.example.author.domain.model;

// Value Object - Name with validation
public record AuthorName(String firstName, String lastName) {
    public AuthorName {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }
    
    public String fullName() {
        return firstName + " " + lastName;
    }
}
