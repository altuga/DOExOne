package jug.istanbul.library;

import java.util.List;
import java.util.Optional;

// DO: Separate behavior - User operations
public class UserOperations {
    private UserOperations() {} // Utility class
    
    // Authenticate user
    public static Optional<User> authenticate(
        List<User> users,
        List<UserCredentials> credentials,
        String email,
        String password
    ) {
        return credentials.stream()
            .filter(cred -> cred.email().equals(email) && cred.password().equals(password))
            .findFirst()
            .flatMap(cred -> users.stream()
                .filter(user -> user.email().equals(email))
                .findFirst());
    }
    
    // Check if user is librarian
    public static boolean isLibrarian(User user) {
        return user.role() == UserRole.LIBRARIAN;
    }
    
    // Check if user is active member
    public static boolean isActiveMember(User user) {
        return user.role() == UserRole.MEMBER && user.status() == MemberStatus.ACTIVE;
    }
    
    // Check if user can borrow
    public static boolean canBorrow(User user) {
        return user.role() == UserRole.MEMBER && user.status() == MemberStatus.ACTIVE;
    }
}
