package com.example.author.domain.service;

import com.example.author.domain.model.BookCount;
import com.example.author.domain.model.ProductivityLevel;
import com.example.author.domain.model.AuthorEntity;

// Domain Service - Author classification logic
public class AuthorClassificationService {
    private AuthorClassificationService() {} // Utility class
    
    public static ProductivityLevel classifyProductivity(BookCount bookCount) {
        return switch (bookCount.value()) {
            case 0 -> ProductivityLevel.ASPIRING;
            case 1, 2, 3, 4, 5 -> ProductivityLevel.BEGINNER;
            case 6, 7, 8, 9, 10 -> ProductivityLevel.DEVELOPING;
            default -> bookCount.value() <= 100 ? ProductivityLevel.ESTABLISHED : ProductivityLevel.PROLIFIC;
        };
    }
    
    public static boolean isProlific(BookCount bookCount) {
        return bookCount.value() > 100;
    }
    
    public static boolean canReceiveAward(AuthorEntity author) {
        return isProlific(author.bookCount()) && 
               !author.name().firstName().trim().isEmpty();
    }
}
