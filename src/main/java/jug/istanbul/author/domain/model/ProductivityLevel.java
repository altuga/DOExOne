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
    
    // Java 21 enhanced pattern matching with switch expressions
    public String getDescription() {
        return switch (this) {
            case ASPIRING -> "Just starting their writing journey";
            case BEGINNER -> "Getting their first books published";
            case DEVELOPING -> "Building their reputation and skills";
            case ESTABLISHED -> "Well-known author with consistent output";
            case PROLIFIC -> "Exceptionally productive and influential author";
        };
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
