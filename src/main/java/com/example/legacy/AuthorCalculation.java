package com.example.legacy;

// Author-specific calculations - separated from data
public class AuthorCalculation {
    private AuthorCalculation() {} // Utility class
    
    public static boolean isProlific(AuthorData author) {
        return author.books() > 100;
    }
    
    public static String getProductivityLevel(AuthorData author) {
        if (author.books() == 0) return "Aspiring";
        if (author.books() <= 5) return "Beginner";
        if (author.books() <= 10) return "Developing";
        if (author.books() <= 100) return "Established";
        return "Prolific";
    }
}
