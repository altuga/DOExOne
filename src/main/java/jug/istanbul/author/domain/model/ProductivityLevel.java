package jug.istanbul.author.domain.model;

// Domain Enum - Productivity levels in the author domain
public enum ProductivityLevel {
    ASPIRING("Aspiring"),
    BEGINNER("Beginner"), 
    DEVELOPING("Developing"),
    ESTABLISHED("Established"),
    PROLIFIC("Prolific");
    
    private final String displayName;
    
    ProductivityLevel(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
