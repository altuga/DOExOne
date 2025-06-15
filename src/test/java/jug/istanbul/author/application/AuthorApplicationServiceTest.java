package jug.istanbul.author.application;

import jug.istanbul.author.domain.model.*;
import jug.istanbul.author.domain.service.AuthorRepository;
import jug.istanbul.author.infrastructure.InMemoryAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorApplicationService Tests - Use Case Orchestration")
class AuthorApplicationServiceTest {
    
    private AuthorRepository repository;
    private AuthorApplicationService service;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryAuthorRepository();
        service = new AuthorApplicationService(repository);
    }
    
    @Test
    @DisplayName("Should create new author successfully")
    void shouldCreateNewAuthor() {
        // When
        AuthorEntity author = service.createAuthor("Isaac", "Asimov");
        
        // Then
        assertNotNull(author);
        assertEquals("Isaac", author.name().firstName());
        assertEquals("Asimov", author.name().lastName());
        assertEquals(0, author.bookCount().value());
        
        // Verify author is persisted
        assertTrue(repository.findById(author.id()).isPresent());
    }
    
    @Test
    @DisplayName("Should reject duplicate author creation")
    void shouldRejectDuplicateAuthor() {
        // Given
        service.createAuthor("Isaac", "Asimov");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> service.createAuthor("Isaac", "Asimov"),
            "Should reject creating duplicate author");
    }
    
    @Test
    @DisplayName("Should publish book for existing author")
    void shouldPublishBookForExistingAuthor() {
        // Given
        AuthorEntity author = service.createAuthor("Isaac", "Asimov");
        AuthorId authorId = author.id();
        
        // When
        AuthorEntity updatedAuthor = service.publishBook(authorId);
        
        // Then
        assertEquals(1, updatedAuthor.bookCount().value());
        
        // Verify persistence
        AuthorEntity persistedAuthor = repository.findById(authorId).orElseThrow();
        assertEquals(1, persistedAuthor.bookCount().value());
    }
    
    @Test
    @DisplayName("Should reject publishing book for non-existent author")
    void shouldRejectPublishingForNonExistentAuthor() {
        // Given
        AuthorId nonExistentId = AuthorId.generate();
        
        // When & Then
        assertThrows(IllegalArgumentException.class,
            () -> service.publishBook(nonExistentId),
            "Should reject publishing book for non-existent author");
    }
    
    @Test
    @DisplayName("Should get author classification correctly")
    void shouldGetAuthorClassification() {
        // Given
        AuthorEntity author = service.createAuthor("Isaac", "Asimov");
        // Publish many books to make prolific
        for (int i = 0; i < 150; i++) {
            author = service.publishBook(author.id());
        }
        
        // When
        AuthorClassificationResult result = service.getAuthorClassification(author.id());
        
        // Then
        assertEquals(author.id(), result.author().id());
        assertEquals(ProductivityLevel.PROLIFIC, result.productivityLevel());
        assertTrue(result.isProlific());
        assertTrue(result.canReceiveAward());
    }
    
    @Test
    @DisplayName("Should get productivity statistics across all authors")
    void shouldGetProductivityStatistics() {
        // Given - Create authors with different productivity levels
        AuthorEntity prolific1 = service.createAuthor("Isaac", "Asimov");
        AuthorEntity prolific2 = service.createAuthor("Philip", "Dick");
        AuthorEntity beginner = service.createAuthor("Jane", "Smith");
        AuthorEntity established = service.createAuthor("John", "Doe");
        
        // Make them prolific/established
        for (int i = 0; i < 150; i++) {
            prolific1 = service.publishBook(prolific1.id());
            prolific2 = service.publishBook(prolific2.id());
        }
        for (int i = 0; i < 50; i++) {
            established = service.publishBook(established.id());
        }
        for (int i = 0; i < 3; i++) {
            beginner = service.publishBook(beginner.id());
        }
        
        // When
        ProductivityStatistics stats = service.getProductivityStatistics();
        
        // Then
        assertEquals(4, stats.totalAuthors());
        assertEquals(2, stats.prolificCount());
        assertEquals(1, stats.establishedCount());
        assertEquals(1, stats.beginnerCount());
        assertEquals(50.0, stats.prolificPercentage(), 0.01);
        assertEquals(2, stats.prolificAuthors().size());
    }
    
    @Test
    @DisplayName("Should demonstrate complete use case workflow")
    void shouldDemonstrateCompleteWorkflow() {
        // Given - Create an author
        AuthorEntity author = service.createAuthor("Isaac", "Asimov");
        AuthorId authorId = author.id();
        
        // When - Author writing career simulation
        // Start as aspiring
        AuthorClassificationResult initial = service.getAuthorClassification(authorId);
        assertEquals(ProductivityLevel.ASPIRING, initial.productivityLevel());
        
        // Publish first books (becomes beginner)
        for (int i = 0; i < 3; i++) {
            service.publishBook(authorId);
        }
        AuthorClassificationResult beginner = service.getAuthorClassification(authorId);
        assertEquals(ProductivityLevel.BEGINNER, beginner.productivityLevel());
        
        // Publish more books (becomes established)
        for (int i = 0; i < 47; i++) { // Total: 50 books
            service.publishBook(authorId);
        }
        AuthorClassificationResult established = service.getAuthorClassification(authorId);
        assertEquals(ProductivityLevel.ESTABLISHED, established.productivityLevel());
        
        // Publish many more books (becomes prolific)
        for (int i = 0; i < 100; i++) { // Total: 150 books
            service.publishBook(authorId);
        }
        AuthorClassificationResult prolific = service.getAuthorClassification(authorId);
        assertEquals(ProductivityLevel.PROLIFIC, prolific.productivityLevel());
        assertTrue(prolific.isProlific());
        assertTrue(prolific.canReceiveAward());
        
        // Then - Verify final state
        AuthorEntity finalAuthor = repository.findById(authorId).orElseThrow();
        assertEquals(150, finalAuthor.bookCount().value());
        assertEquals("Isaac Asimov", finalAuthor.name().fullName());
    }
}
