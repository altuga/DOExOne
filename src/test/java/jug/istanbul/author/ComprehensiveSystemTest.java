package jug.istanbul.author;

import jug.istanbul.author.application.*;
import jug.istanbul.author.domain.model.*;
import jug.istanbul.author.domain.service.AuthorRepository;
import jug.istanbul.author.infrastructure.InMemoryAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comprehensive System Test - DDD + DOP Integration")
class ComprehensiveSystemTest {
    
    private AuthorRepository repository;
    private AuthorApplicationService applicationService;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryAuthorRepository();
        applicationService = new AuthorApplicationService(repository);
    }
    
    @Test
    @DisplayName("Should demonstrate complete DDD + DOP system with multiple authors")
    void shouldDemonstrateCompleteSystem() {
        // === SCENARIO: Create diverse author portfolio ===
        
        // 1. Create authors at different career stages
        AuthorEntity asimov = applicationService.createAuthor("Isaac", "Asimov");
        AuthorEntity clarke = applicationService.createAuthor("Arthur", "Clarke");
        AuthorEntity newcomer = applicationService.createAuthor("New", "Writer");
        
        // 2. Simulate different publishing careers
        
        // Isaac Asimov: Established prolific author (150 books)
        for (int i = 0; i < 150; i++) {
            asimov = applicationService.publishBook(asimov.id());
        }
        
        // Arthur Clarke: Established author (50 books)
        for (int i = 0; i < 50; i++) {
            clarke = applicationService.publishBook(clarke.id());
        }
        
        // New Writer: Beginner (3 books)
        for (int i = 0; i < 3; i++) {
            newcomer = applicationService.publishBook(newcomer.id());
        }
        
        // === VERIFICATION: System-wide analysis ===
        
        // 3. Verify individual author classifications
        AuthorClassificationResult asimovResult = 
            applicationService.getAuthorClassification(asimov.id());
        AuthorClassificationResult clarkeResult = 
            applicationService.getAuthorClassification(clarke.id());
        AuthorClassificationResult newcomerResult = 
            applicationService.getAuthorClassification(newcomer.id());
        
        // Asimov should be prolific
        assertAll("Isaac Asimov classification",
            () -> assertEquals(ProductivityLevel.PROLIFIC, asimovResult.productivityLevel()),
            () -> assertTrue(asimovResult.isProlific()),
            () -> assertTrue(asimovResult.canReceiveAward()),
            () -> assertEquals(150, asimovResult.author().bookCount().value())
        );
        
        // Clarke should be established
        assertAll("Arthur Clarke classification",
            () -> assertEquals(ProductivityLevel.ESTABLISHED, clarkeResult.productivityLevel()),
            () -> assertFalse(clarkeResult.isProlific()),
            () -> assertFalse(clarkeResult.canReceiveAward()),
            () -> assertEquals(50, clarkeResult.author().bookCount().value())
        );
        
        // Newcomer should be beginner
        assertAll("New Writer classification",
            () -> assertEquals(ProductivityLevel.BEGINNER, newcomerResult.productivityLevel()),
            () -> assertFalse(newcomerResult.isProlific()),
            () -> assertFalse(newcomerResult.canReceiveAward()),
            () -> assertEquals(3, newcomerResult.author().bookCount().value())
        );
        
        // 4. Verify system-wide statistics
        ProductivityStatistics stats = applicationService.getProductivityStatistics();
        
        assertAll("System statistics",
            () -> assertEquals(1, stats.beginnerCount()),
            () -> assertEquals(1, stats.establishedCount()),
            () -> assertEquals(1, stats.prolificCount()),
            () -> assertEquals(1, stats.prolificAuthors().size()),
            () -> assertEquals("Isaac Asimov", stats.prolificAuthors().get(0).name().fullName())
        );
        
        // 5. Verify immutability throughout operations
        // Repository should maintain current state correctly
        assertEquals(3, repository.findAll().size());
        
        // Each author's identity should be preserved
        assertTrue(repository.findById(asimov.id()).isPresent());
        assertTrue(repository.findById(clarke.id()).isPresent());
        assertTrue(repository.findById(newcomer.id()).isPresent());
        
        // === DEMONSTRATE PURE FUNCTION BEHAVIOR ===
        
        // 6. Domain services should be pure (same input -> same output)
        BookCount fiftyBooks = new BookCount(50);
        ProductivityLevel level1 = 
            jug.istanbul.author.domain.service.AuthorClassificationService
                .classifyProductivity(fiftyBooks);
        ProductivityLevel level2 = 
            jug.istanbul.author.domain.service.AuthorClassificationService
                .classifyProductivity(fiftyBooks);
        
        assertEquals(level1, level2, "Pure functions should return consistent results");
        assertEquals(ProductivityLevel.ESTABLISHED, level1);
        
        // 7. Value objects should be immutable
        AuthorName originalName = newcomer.name();
        BookCount originalBooks = newcomer.bookCount();
        
        // Operations create new instances
        BookCount moreBooks = originalBooks.add(10);
        assertEquals(3, originalBooks.value(), "Original BookCount should be unchanged");
        assertEquals(13, moreBooks.value(), "New BookCount should have added value");
        
        System.out.println("✅ All DDD + DOP principles verified successfully!");
        System.out.println("📊 Final Statistics:");
        System.out.println("   - Total Authors: " + repository.findAll().size());
        System.out.println("   - Prolific Authors: " + stats.prolificCount());
        System.out.println("   - Established Authors: " + stats.establishedCount());
        System.out.println("   - Beginner Authors: " + stats.beginnerCount());
    }
}
