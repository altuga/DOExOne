package com.example.author.application;

import com.example.author.domain.model.AuthorEntity;
import com.example.author.domain.model.ProductivityLevel;

// DTO - Result of author classification
public record AuthorClassificationResult(
    AuthorEntity author,
    ProductivityLevel productivityLevel,
    boolean isProlific,
    boolean canReceiveAward
) {}
