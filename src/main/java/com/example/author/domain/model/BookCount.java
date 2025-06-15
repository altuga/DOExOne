package com.example.author.domain.model;

// Value Object - Book count with domain rules
public record BookCount(int value) {
    public BookCount {
        if (value < 0) {
            throw new IllegalArgumentException("Book count cannot be negative");
        }
    }
    
    public BookCount increment() {
        return new BookCount(value + 1);
    }
    
    public BookCount add(int books) {
        return new BookCount(value + books);
    }
}
