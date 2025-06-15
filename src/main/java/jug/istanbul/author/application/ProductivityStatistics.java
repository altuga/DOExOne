package jug.istanbul.author.application;

import jug.istanbul.author.domain.model.AuthorEntity;

import java.util.List;

// DTO - Productivity statistics across all authors
public record ProductivityStatistics(
    long beginnerCount,
    long establishedCount,
    long prolificCount,
    List<AuthorEntity> prolificAuthors
) {
    public long totalAuthors() {
        return beginnerCount + establishedCount + prolificCount;
    }
    
    public double prolificPercentage() {
        long total = totalAuthors();
        return total > 0 ? (double) prolificCount / total * 100 : 0.0;
    }
}
