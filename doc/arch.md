


# Project Architecture

## Layered Architecture

### Core Layers
- **API Layer** (Controllers): Handles HTTP requests/responses, validation, and API documentation
- **Service Layer**: Contains business logic and transaction management
- **Repository Layer**: Data access and persistence operations
- **Entity Layer**: Domain models representing database tables

### Supporting Layers
- **DTO Layer**: Data transfer objects to separate API contracts from internal models
- **Config Layer**: Application configuration and bean definitions
- **Aspect Layer**: Cross-cutting concerns like logging, security, and validation
- **Utility Layer**: Helper classes and common functionality

## Separation of Concerns
Each layer has a specific responsibility:
- API Layer should not contain business logic
- Service Layer should not directly interact with HTTP or database
- Repository Layer should focus only on data access
- Entities should be simple POJOs without business logic

## Packages Structure
```
src/
├── api/           # REST controllers, request/response DTOs
├── service/       # Business logic services
├── repository/    # Data access repositories
├── entity/        # Domain models
├── dto/           # Data transfer objects
├── config/        # Configuration classes
├── aspect/        # AOP aspects
├── util/          # Utility classes
└── ApplicationSpr.java  # Application entry point
```
