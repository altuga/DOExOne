package jug.istanbul.author.infrastructure;

import jug.istanbul.author.domain.model.AuthorEntity;
import jug.istanbul.author.domain.model.AuthorId;
import jug.istanbul.author.domain.model.ProductivityLevel;
import jug.istanbul.author.domain.service.AuthorRepository;
import jug.istanbul.author.domain.service.AuthorClassificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Simple in-memory implementation for demo
public class InMemoryAuthorRepository implements AuthorRepository {
    private final List<AuthorEntity> authors = new ArrayList<>();
    
    @Override
    public AuthorEntity save(AuthorEntity author) {
        // Remove existing if updating
        authors.removeIf(a -> a.id().equals(author.id()));
        authors.add(author);
        return author;
    }
    
    @Override
    public Optional<AuthorEntity> findById(AuthorId id) {
        return authors.stream()
            .filter(author -> author.id().equals(id))
            .findFirst();
    }
    
    @Override
    public List<AuthorEntity> findAll() {
        return new ArrayList<>(authors);
    }
    
    @Override
    public void deleteById(AuthorId id) {
        authors.removeIf(author -> author.id().equals(id));
    }
    
    @Override
    public List<AuthorEntity> findProlificAuthors() {
        return authors.stream()
            .filter(author -> AuthorClassificationService.isProlific(author.bookCount()))
            .toList();
    }
    
    @Override
    public List<AuthorEntity> findByProductivityLevel(ProductivityLevel level) {
        return authors.stream()
            .filter(author -> AuthorClassificationService.classifyProductivity(author.bookCount()) == level)
            .toList();
    }
    
    @Override
    public Optional<AuthorEntity> findByFullName(String firstName, String lastName) {
        return authors.stream()
            .filter(author -> author.name().firstName().equals(firstName) && 
                             author.name().lastName().equals(lastName))
            .findFirst();
    }
    
    @Override
    public long countByProductivityLevel(ProductivityLevel level) {
        return authors.stream()
            .filter(author -> AuthorClassificationService.classifyProductivity(author.bookCount()) == level)
            .count();
    }
    
    @Override
    public List<AuthorEntity> findTopAuthorsByBookCount(int limit) {
        return authors.stream()
            .sorted((a, b) -> Integer.compare(b.bookCount().value(), a.bookCount().value()))
            .limit(limit)
            .toList();
    }
}
