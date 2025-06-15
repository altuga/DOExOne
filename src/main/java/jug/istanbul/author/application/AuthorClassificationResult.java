package jug.istanbul.author.application;

import jug.istanbul.author.domain.model.AuthorEntity;
import jug.istanbul.author.domain.model.ProductivityLevel;

// DTO - Result of author classification
public record AuthorClassificationResult(
    AuthorEntity author,
    ProductivityLevel productivityLevel,
    boolean isProlific,
    boolean canReceiveAward
) {}
