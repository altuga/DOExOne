# DDD Package Structure Documentation

This project demonstrates how **Data-Oriented Programming (DOP)** can be effectively combined with **Domain-Driven Design (DDD)** tactical patterns using proper package organization.

## 📦 Package Structure

```
jug.istanbul.author/
├── domain/
│   ├── model/              # Domain Entities and Value Objects
│   │   ├── AuthorEntity.java     # Aggregate Root (Entity)
│   │   ├── AuthorId.java         # Value Object (Identity)
│   │   ├── AuthorName.java       # Value Object (Name)
│   │   ├── BookCount.java        # Value Object (Count)
│   │   └── ProductivityLevel.java # Domain Enum
│   └── service/            # Domain Services and Repository Interfaces
│       ├── AuthorClassificationService.java  # Domain Service (Pure Functions)
│       └── AuthorRepository.java             # Repository Interface
├── application/            # Application Services and DTOs
│   ├── AuthorApplicationService.java    # Use Case Orchestration
│   ├── AuthorClassificationResult.java  # DTO
│   └── ProductivityStatistics.java      # DTO
├── infrastructure/         # Technical Implementation
│   └── InMemoryAuthorRepository.java    # Repository Implementation
└── legacy/                # Original Simple Examples
    ├── Author.java              # Original OOP approach
    ├── AuthorData.java          # Simple record
    ├── NameCalculation.java     # Simple pure function
    └── AuthorCalculation.java   # Simple domain logic
```

## 🎯 Package Responsibilities

### **Domain Layer** (`domain/`)
- **`domain.model`**: Core business entities, value objects, and domain concepts
- **`domain.service`**: Domain services with business logic and repository contracts

### **Application Layer** (`application/`)
- **Use case orchestration**: Coordinates domain objects and services
- **DTOs**: Data transfer objects for application boundaries
- **Application services**: Entry points for business operations

### **Infrastructure Layer** (`infrastructure/`)
- **Repository implementations**: Data persistence logic
- **External service adapters**: Integration with external systems
- **Technical concerns**: Framework-specific code

### **Legacy Package** (`legacy/`)
- **Simple examples**: Basic DOP demonstrations
- **Learning materials**: Step-by-step progression examples

## 🏗️ DDD + DOP Benefits

### **Clean Architecture**
- **Dependency direction**: Infrastructure → Application → Domain
- **Domain isolation**: Core business logic independent of frameworks
- **Testability**: Each layer can be tested independently

### **Domain-Driven Design**
- **Ubiquitous language**: Package names reflect business concepts
- **Bounded contexts**: Clear separation of author management domain
- **Rich domain model**: Value objects encapsulate business rules

### **Data-Oriented Programming**
- **Immutable data**: All domain objects are records
- **Pure functions**: Domain services have no side effects
- **Data/behavior separation**: Clear boundaries between data and operations

## 🚀 Usage Examples

### **Simple DOP Example** (Legacy)
```java
// Basic data-oriented approach
var authorData = new AuthorData("Isaac", "Asimov", 500);
String fullName = NameCalculation.fullName(authorData);
```

### **DDD + DOP Example** (Structured)
```java
// Rich domain model with DDD patterns
AuthorEntity author = AuthorEntity.create("Isaac", "Asimov");
author = author.publishBooks(150);

ProductivityLevel level = AuthorClassificationService
    .classifyProductivity(author.bookCount());
```

## 📋 Key Design Principles

1. **Package by Feature**: Organized around business capabilities
2. **Dependency Inversion**: Domain doesn't depend on infrastructure
3. **Single Responsibility**: Each package has a clear purpose
4. **Domain Purity**: Core business logic is framework-agnostic
5. **Immutability**: All data structures are immutable records

## 🔄 Evolution Path

The project shows progression from:
1. **Simple OOP** (`legacy.Author`) → Basic object-oriented approach
2. **Basic DOP** (`legacy.AuthorData`) → Simple data-oriented programming
3. **DDD + DOP** (`author.domain`) → Sophisticated domain modeling with DOP

This structure scales well for real-world applications and maintains clean separation of concerns while leveraging modern Java features.
