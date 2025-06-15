package com.example.author.application;

import com.example.author.domain.model.AuthorEntity;
import com.example.author.domain.model.AuthorId;
import com.example.author.domain.service.AuthorRepository;
import com.example.author.domain.service.AuthorClassificationService;
import com.example.author.domain.model.ProductivityLevel;

import java.util.List;
import java.util.Optional;

// Application Service - Orchestrates domain operations
public class AuthorApplicationService {
    private final AuthorRepository repository;
    
    public AuthorApplicationService(AuthorRepository repository) {
        this.repository = repository;
    }
    
    // Use case: Create a new author
    public AuthorEntity createAuthor(String firstName, String lastName) {
        // Check if author already exists
        Optional<AuthorEntity> existing = repository.findByFullName(firstName, lastName);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Author already exists: " + firstName + " " + lastName);
        }
        
        AuthorEntity newAuthor = AuthorEntity.create(firstName, lastName);
        return repository.save(newAuthor);
    }
    
    // Use case: Author publishes a book
    public AuthorEntity publishBook(AuthorId authorId) {
        AuthorEntity author = repository.findById(authorId)
            .orElseThrow(() -> new IllegalArgumentException("Author not found: " + authorId.value()));
        
        AuthorEntity updatedAuthor = author.publishBook();
        return repository.save(updatedAuthor);
    }
    
    // Use case: Get author classification
    public AuthorClassificationResult getAuthorClassification(AuthorId authorId) {
        AuthorEntity author = repository.findById(authorId)
            .orElseThrow(() -> new IllegalArgumentException("Author not found: " + authorId.value()));
        
        ProductivityLevel level = AuthorClassificationService.classifyProductivity(author.bookCount());
        boolean isProlific = AuthorClassificationService.isProlific(author.bookCount());
        boolean canReceiveAward = AuthorClassificationService.canReceiveAward(author);
        
        return new AuthorClassificationResult(author, level, isProlific, canReceiveAward);
    }
    
    // Use case: Get productivity statistics
    public ProductivityStatistics getProductivityStatistics() {
        List<AuthorEntity> prolificAuthors = repository.findProlificAuthors();
        long beginnerCount = repository.countByProductivityLevel(ProductivityLevel.BEGINNER);
        long establishedCount = repository.countByProductivityLevel(ProductivityLevel.ESTABLISHED);
        long prolificCount = repository.countByProductivityLevel(ProductivityLevel.PROLIFIC);
        
        return new ProductivityStatistics(beginnerCount, establishedCount, prolificCount, prolificAuthors);
    }
}
