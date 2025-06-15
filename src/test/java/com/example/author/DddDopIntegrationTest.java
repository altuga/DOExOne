package com.example.author;

import com.example.author.application.*;
import com.example.author.domain.model.*;
import com.example.author.domain.service.AuthorRepository;
import com.example.author.infrastructure.InMemoryAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DDD + DOP Integration Tests - Full System Verification")
class DddDopIntegrationTest {
    
    private AuthorRepository repository;
    private AuthorApplicationService applicationService;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryAuthorRepository();
        applicationService = new AuthorApplicationService(repository);
    }
    
    @Test
    @DisplayName("Should demonstrate complete DDD + DOP architecture")
    void shouldDemonstrateCompleteDddDopArchitecture() {
        // === DOMAIN LAYER DEMONSTRATION ===
        
        // 1. Value Objects (DOP Records with business rules)
        AuthorName name = new AuthorName("Isaac", "Asimov");
        BookCount bookCount = new BookCount(0);
        AuthorId id = AuthorId.generate();
        
        // Value objects are immutable and have business behavior
        assertEquals("Isaac Asimov", name.fullName());
        BookCount moreBooks = bookCount.add(100);
        assertEquals(0, bookCount.value()); // Original unchanged
        assertEquals(100, moreBooks.value()); // New instance
        
        // 2. Entity (DOP Record with identity and business operations)
        AuthorEntity author = new AuthorEntity(id, name, bookCount);
        AuthorEntity publishedAuthor = author.publishBooks(150);
        
        // Entity maintains identity while state changes immutably
        assertEquals(author.id(), publishedAuthor.id()); // Same identity
        assertNotEquals(author.bookCount(), publishedAuthor.bookCount()); // Different state
        
        // === APPLICATION LAYER DEMONSTRATION ===
        
        // 3. Use Case Orchestration
        AuthorEntity createdAuthor = applicationService.createAuthor("Philip", "Dick");
        AuthorEntity publishedManagedAuthor = applicationService.publishBook(createdAuthor.id());
        
        // 4. Domain Service (Pure Functions)
        ProductivityLevel level = 
            com.example.author.domain.service.AuthorClassificationService
                .classifyProductivity(publishedManagedAuthor.bookCount());
        assertEquals(ProductivityLevel.BEGINNER, level);
        
        // === INFRASTRUCTURE LAYER DEMONSTRATION ===
        
        // 5. Repository (Persistent storage)
        assertTrue(repository.findById(publishedManagedAuthor.id()).isPresent());
        assertEquals(1, repository.findAll().size());
        
        // === END-TO-END WORKFLOW ===
        
        // 6. Complete business scenario
        AuthorClassificationResult result = 
            applicationService.getAuthorClassification(publishedManagedAuthor.id());
        
        assertAll("Complete DDD + DOP verification",
            () -> assertEquals(publishedManagedAuthor.id(), result.author().id()),
            () -> assertEquals(ProductivityLevel.BEGINNER, result.productivityLevel()),
            () -> assertFalse(result.isProlific()),
            () -> assertFalse(result.canReceiveAward())
        );
    }
    
    @Test
    @DisplayName("Should demonstrate immutability throughout the system")
    void shouldDemonstrateSystemWideImmutability() {
        // Given - Create initial state
        AuthorEntity original = applicationService.createAuthor("Isaac", "Asimov");
        AuthorId authorId = original.id();
        
        // When - Perform multiple operations
        AuthorEntity step1 = applicationService.publishBook(authorId);
        AuthorEntity step2 = applicationService.publishBook(authorId);
        AuthorEntity step3 = applicationService.publishBook(authorId);
        
        // Then - Verify immutability at each step
        assertEquals(0, original.bookCount().value()); // Original unchanged
        assertEquals(1, step1.bookCount().value());
        assertEquals(2, step2.bookCount().value());
        assertEquals(3, step3.bookCount().value());
        
        // All have same identity but different states
        assertEquals(authorId, step1.id());
        assertEquals(authorId, step2.id());
        assertEquals(authorId, step3.id());
        
        // Final repository state reflects last operation
        AuthorEntity persisted = repository.findById(authorId).orElseThrow();
        assertEquals(3, persisted.bookCount().value());
    }
    
    @Test
    @DisplayName("Should demonstrate pure function behavior in domain services")
    void shouldDemonstratePureFunctions() {
        // Given
        BookCount bookCount = new BookCount(75);
        
        // When - Call pure functions multiple times
        ProductivityLevel level1 = 
            com.example.author.domain.service.AuthorClassificationService
                .classifyProductivity(bookCount);
        ProductivityLevel level2 = 
            com.example.author.domain.service.AuthorClassificationService
                .classifyProductivity(bookCount);
        
        boolean isProlific1 = 
            com.example.author.domain.service.AuthorClassificationService
                .isProlific(bookCount);
        boolean isProlific2 = 
            com.example.author.domain.service.AuthorClassificationService
                .isProlific(bookCount);
        
        // Then - Pure functions always return same result
        assertEquals(level1, level2);
        assertEquals(isProlific1, isProlific2);
        assertEquals(ProductivityLevel.ESTABLISHED, level1);
        assertFalse(isProlific1);
        
        // And input is never modified
        assertEquals(75, bookCount.value());
    }
    
    @Test
    @DisplayName("Should demonstrate clean architecture dependency flow")
    void shouldDemonstrateCleanArchitecture() {
        // This test demonstrates proper dependency flow:
        // Infrastructure -> Application -> Domain
        
        // 1. Domain layer is pure (no dependencies on outer layers)
        AuthorName name = new AuthorName("Test", "Author");
        BookCount books = new BookCount(50);
        ProductivityLevel level = 
            com.example.author.domain.service.AuthorClassificationService
                .classifyProductivity(books);
        
        // Domain objects work independently
        assertEquals("Test Author", name.fullName());
        assertEquals(ProductivityLevel.ESTABLISHED, level);
        
        // 2. Application layer depends only on domain
        AuthorEntity author = applicationService.createAuthor("Clean", "Architecture");
        
        // 3. Infrastructure implements domain contracts
        assertTrue(repository.findById(author.id()).isPresent());
        
        // 4. Full system integration preserves all principles
        for (int i = 0; i < 101; i++) {
            applicationService.publishBook(author.id());
        }
        
        ProductivityStatistics stats = applicationService.getProductivityStatistics();
        assertEquals(1, stats.prolificCount());
        
        // Verify the entire system maintains immutability and purity
        AuthorEntity finalAuthor = repository.findById(author.id()).orElseThrow();
        assertEquals(101, finalAuthor.bookCount().value());
        assertEquals("Clean Architecture", finalAuthor.name().fullName());
    }
}
