package com.example;

import com.example.author.domain.model.AuthorEntity;
import com.example.author.domain.model.AuthorName;
import com.example.author.domain.model.BookCount;
import com.example.author.domain.service.AuthorRepository;
import com.example.author.application.AuthorApplicationService;
import com.example.author.application.AuthorClassificationResult;
import com.example.author.application.ProductivityStatistics;
import com.example.author.infrastructure.InMemoryAuthorRepository;

// Demo application showing DDD tactical patterns with DOP
public class DddDopDemo {
    public static void main(String[] args) {
        System.out.println("=== DDD Tactical Patterns + Data-Oriented Programming Demo ===\n");
        
        // Setup
        AuthorRepository repository = new InMemoryAuthorRepository();
        AuthorApplicationService service = new AuthorApplicationService(repository);
        
        try {
            // Use case 1: Create authors
            System.out.println("1. Creating authors...");
            AuthorEntity isaac = service.createAuthor("Isaac", "Asimov");
            AuthorEntity jane = service.createAuthor("Jane", "Smith");
            
            System.out.println("Created: " + isaac.name().fullName());
            System.out.println("Created: " + jane.name().fullName());
            
            // Use case 2: Publish books
            System.out.println("\n2. Publishing books...");
            isaac = service.publishBook(isaac.id());
            isaac = isaac.publishBooks(150); // Simulate publishing many books
            repository.save(isaac);
            
            jane = service.publishBook(jane.id());
            jane = jane.publishBooks(3);
            repository.save(jane);
            
            System.out.println(isaac.name().fullName() + " has " + isaac.bookCount().value() + " books");
            System.out.println(jane.name().fullName() + " has " + jane.bookCount().value() + " books");
            
            // Use case 3: Get classifications
            System.out.println("\n3. Author classifications...");
            AuthorClassificationResult isaacResult = service.getAuthorClassification(isaac.id());
            AuthorClassificationResult janeResult = service.getAuthorClassification(jane.id());
            
            printClassification(isaacResult);
            printClassification(janeResult);
            
            // Use case 4: Get statistics
            System.out.println("\n4. Productivity statistics...");
            ProductivityStatistics stats = service.getProductivityStatistics();
            System.out.println("Total authors: " + stats.totalAuthors());
            System.out.println("Prolific authors: " + stats.prolificCount());
            System.out.println("Prolific percentage: " + String.format("%.1f%%", stats.prolificPercentage()));
            
            // Demonstrate Value Object immutability
            System.out.println("\n5. Demonstrating immutability...");
            AuthorName originalName = isaac.name();
            BookCount originalBooks = isaac.bookCount();
            
            System.out.println("Original: " + originalName.fullName() + " with " + originalBooks.value() + " books");
            
            // Operations return new instances
            BookCount moreBooks = originalBooks.add(50);
            System.out.println("After adding 50 books: " + moreBooks.value() + " (original unchanged: " + originalBooks.value() + ")");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void printClassification(AuthorClassificationResult result) {
        System.out.println(result.author().name().fullName() + ":");
        System.out.println("  - Productivity Level: " + result.productivityLevel());
        System.out.println("  - Is Prolific: " + result.isProlific());
        System.out.println("  - Can Receive Award: " + result.canReceiveAward());
    }
}
