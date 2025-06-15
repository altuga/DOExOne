package jug.istanbul.legacy;

// Behavior separated from data - pure functions
public class NameCalculation {
    private NameCalculation() {} // Utility class
    
    // Function that works with AuthorData
    public static String fullName(AuthorData data) {
        return data.firstName() + " " + data.lastName();
    }
}
