# Data-Oriented Programming + Domain-Driven Design

A comprehensive Maven project demonstrating how to combine **Data-Oriented Programming (DOP)** principles with **Domain-Driven Design (DDD)** tactical patterns using modern Java features.

## Quick Start

### Prerequisites
- Java 21+ 
- Maven 3.8+

### Run the Demonstrations
```bash
# Basic DOP principles demonstration
mvn exec:java -Dexec.mainClass="jug.istanbul.App"

# Advanced DDD + DOP integrated architecture
mvn exec:java -Dexec.mainClass="jug.istanbul.DddDopDemo"
```

### Execute Tests
```bash
# Run all tests
mvn test

# Run specific test suites
mvn test -Dtest="DddDopIntegrationTest"
mvn test -Dtest="ComprehensiveSystemTest"
```

## Project Overview

This project showcases the evolution from traditional object-oriented programming to modern, functional approaches that combine:

- **Data-Oriented Programming (DOP)**: Immutable data structures, pure functions, data/behavior separation
- **Domain-Driven Design (DDD)**: Strategic patterns, tactical patterns, clean architecture
- **Modern Java**: Records, switch expressions, pattern matching, functional programming

## Architecture

### Layer Structure
```
┌─────────────────────────────────────┐
│         Application Layer           │  ← Use Cases & DTOs
├─────────────────────────────────────┤
│           Domain Layer              │  ← Business Logic (Pure)
├─────────────────────────────────────┤
│       Infrastructure Layer         │  ← Technical Implementations
└─────────────────────────────────────┘
```

### Package Organization
```
jug.istanbul.author/
├── domain/
│   ├── model/              # Value Objects & Entities
│   │   ├── AuthorEntity    # Core business entity
│   │   ├── AuthorName      # Value object (immutable)
│   │   ├── BookCount       # Value object (typed primitive)
│   │   ├── AuthorId        # Identity value object
│   │   └── ProductivityLevel # Business enumeration
│   └── service/            # Domain Services & Contracts
│       ├── AuthorClassificationService # Pure functions
│       └── AuthorRepository           # Domain contract
├── application/            # Use Cases & Data Transfer
│   ├── AuthorApplicationService       # Orchestration
│   ├── AuthorClassificationResult     # Output DTO
│   └── ProductivityStatistics         # Analytics DTO
├── infrastructure/         # Technical Implementation
│   └── InMemoryAuthorRepository      # Repository impl
└── legacy/                # Original DOP examples
    ├── Author, AuthorData            # OOP vs DOP comparison
    └── NameCalculation, AuthorCalculation # Pure functions
```

## Key Features Demonstrated

### Data-Oriented Programming Principles
- **Immutable Data Structures**: Java records for all domain objects
- **Pure Functions**: Domain services with no side effects  
- **Data/Behavior Separation**: Clear boundaries between data and operations
- **Functional Composition**: Operations return new instances instead of mutation

### Domain-Driven Design Patterns
- **Value Objects**: Type-safe, immutable data holders with business meaning
- **Entities**: Objects with identity and lifecycle
- **Domain Services**: Stateless operations that don't belong to any entity
- **Repository Pattern**: Abstract data access with domain contracts
- **Application Services**: Use case orchestration and boundary management

### Modern Java Features
- **Records**: Concise immutable data holders
- **Switch Expressions**: Pattern matching for business logic
- **Sealed Classes**: Type-safe alternatives to enums
- **Stream API**: Functional data processing
- **Optional**: Null safety and explicit absence

## Usage Examples

### Creating and Working with Authors
```java
// Create author using application service
AuthorEntity author = applicationService.createAuthor("Isaac", "Asimov");

// Publish books (immutable operations)
AuthorEntity prolificAuthor = author.publishBooks(150);

// Pure function classification
ProductivityLevel level = AuthorClassificationService
    .classifyProductivity(prolificAuthor.bookCount());
// Result: PROLIFIC

// Get comprehensive analysis
AuthorClassificationResult result = 
    applicationService.getAuthorClassification(author.id());
```

### Domain Logic Examples
```java
// Value object operations (immutable)
BookCount books = new BookCount(50);
BookCount moreBooks = books.add(25); // Returns new instance
// books.value() = 50 (unchanged)
// moreBooks.value() = 75

// Pure function behavior
AuthorName name = new AuthorName("Arthur", "Clarke");
String fullName = name.fullName(); // "Arthur Clarke"

// Business rule enforcement
ProductivityLevel level = switch (bookCount.value()) {
    case 0 -> ProductivityLevel.ASPIRING;
    case 1, 2, 3, 4, 5 -> ProductivityLevel.BEGINNER;
    case 6, 7, 8, 9, 10 -> ProductivityLevel.DEVELOPING;
    default -> bookCount.value() <= 100 ? 
        ProductivityLevel.ESTABLISHED : ProductivityLevel.PROLIFIC;
};
```

## Test Coverage

- **Unit Tests**: Domain objects, value objects, pure functions
- **Integration Tests**: End-to-end workflows across all layers  
- **System Tests**: Multi-author scenarios and complex business rules
- **Property Tests**: Immutability, identity preservation, pure function behavior

## Documentation

- **[Implementation Summary](IMPLEMENTATION_SUMMARY.md)**: Detailed overview of completed features and architectural decisions
- **[DDD Package Structure](DDD_PACKAGE_STRUCTURE.md)**: Comprehensive guide to domain-driven package organization
- **[Copilot Instructions](copilot-instructions.md)**: Development guidelines and AI assistant configuration

## Learning Path

This project demonstrates the evolution from simple procedural code to enterprise-ready architecture:

1. **Traditional OOP** → **Data-Oriented Programming** (Records, immutability)
2. **Mutable State** → **Functional Operations** (Pure functions, new instances)
3. **Procedural Logic** → **Domain Services** (Business rules, type safety)
4. **Flat Structure** → **Layered Architecture** (Clean architecture, dependency inversion)
5. **Basic Tests** → **Comprehensive Verification** (Property testing, integration scenarios)

## Development Setup

### Build the Project
```bash
mvn clean compile
```

### Run All Tests with Coverage
```bash
mvn clean test
```

### Package the Application
```bash
mvn clean package
```

### Clean and Rebuild
```bash
mvn clean install
```

## Best Practices Highlighted

- **Immutability by Default**: All domain objects are immutable records
- **Pure Functions**: Domain logic has no side effects and is easily testable
- **Strong Typing**: Custom value objects prevent primitive obsession
- **Dependency Inversion**: High-level modules don't depend on low-level details
- **Single Responsibility**: Each class has one clear purpose
- **Test-Driven Verification**: Behavior is verified through comprehensive test suites

## Key Takeaways

This implementation serves as a reference for building modern Java applications that successfully combine:

- **Functional Programming Benefits**: Immutability, pure functions, composability
- **Enterprise Architecture Patterns**: Clean architecture, domain modeling, separation of concerns  
- **Modern Java Capabilities**: Records, pattern matching, type safety, null safety
- **Comprehensive Testing**: Unit, integration, and system-level verification

Perfect for developers looking to modernize their Java applications with cutting-edge patterns while maintaining enterprise-grade architecture standards.