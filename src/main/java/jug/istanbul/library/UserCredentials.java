package jug.istanbul.library;

// DO: Simple immutable data - User credentials
public record UserCredentials(String email, String password) {
    public UserCredentials {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }
}
