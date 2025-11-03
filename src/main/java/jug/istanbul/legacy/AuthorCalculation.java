package jug.istanbul.legacy;

// Author-specific calculations - separated from data
public class AuthorCalculation {
    private AuthorCalculation() {} // Utility class
    
    public static boolean isProlific(AuthorData author) {
        return author.books() > 100;
    }
    
    public static String getProductivityLevel(AuthorData author) {
        return switch (author.books()) {
            case 0 -> "Aspiring";
            case 1, 2, 3, 4, 5 -> "Beginner";
            case 6, 7, 8, 9, 10 -> "Developing";
            default -> author.books() <= 100 ? "Established" : "Prolific";
        };
    }
}
