package com.example.author.domain.service;

import com.example.author.domain.model.AuthorEntity;
import com.example.author.domain.model.AuthorId;
import com.example.author.domain.model.ProductivityLevel;

import java.util.List;
import java.util.Optional;

// Repository Interface - Following DDD repository pattern
public interface AuthorRepository {
    
    // Basic CRUD operations
    AuthorEntity save(AuthorEntity author);
    Optional<AuthorEntity> findById(AuthorId id);
    List<AuthorEntity> findAll();
    void deleteById(AuthorId id);
    
    // Domain-specific queries
    List<AuthorEntity> findProlificAuthors();
    List<AuthorEntity> findByProductivityLevel(ProductivityLevel level);
    Optional<AuthorEntity> findByFullName(String firstName, String lastName);
    
    // Aggregate queries
    long countByProductivityLevel(ProductivityLevel level);
    List<AuthorEntity> findTopAuthorsByBookCount(int limit);
}
