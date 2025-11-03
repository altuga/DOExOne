package jug.istanbul.library;

// DO: Simple immutable data - User entity
public record User(
    String userId,
    String name,
    String email,
    UserRole role,
    MemberStatus status
) {
    // Factory method for member
    public static User createMember(String userId, String name, String email) {
        return new User(userId, name, email, UserRole.MEMBER, MemberStatus.ACTIVE);
    }
    
    // Factory method for librarian
    public static User createLibrarian(String userId, String name, String email) {
        return new User(userId, name, email, UserRole.LIBRARIAN, MemberStatus.ACTIVE);
    }
    
    // Immutable operations return new instances
    public User block() {
        return new User(userId, name, email, role, MemberStatus.BLOCKED);
    }
    
    public User unblock() {
        return new User(userId, name, email, role, MemberStatus.ACTIVE);
    }
}
