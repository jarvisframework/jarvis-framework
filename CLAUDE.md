# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Jarvis Framework is a comprehensive Java rapid development framework built on Spring Boot and MyBatis. It provides enterprise-level development capabilities including security, database operations, web MVC, OAuth2, and microservices support.

## Build and Development

### Build Commands
- **Build entire project**: `mvn clean install`
- **Skip tests**: `mvn clean install -Dmaven.test.skip=true`
- **Build specific module**: `mvn clean install -pl <module-name>`
- **Run tests**: `mvn test` (tests are configured to run only classes ending with *Test.java)

### Development Environment
- **Java Version**: JDK 1.8+
- **Spring Boot**: 2.4.4
- **Spring Cloud**: 2020.0.2
- **Build Tool**: Maven
- **Code Quality**: Checkstyle, SpotBugs, JaCoCo coverage

### Code Quality Tools
- **Checkstyle**: Configuration in `jarvis-dev-ops/checkstyle/checkstyle.xml`
- **SpotBugs**: Static analysis for bug detection
- **JaCoCo**: Code coverage reporting
- **Enforcer Plugin**: Ensures dependency compatibility

## Project Architecture

### Module Structure
```
jarvis-framework/
├── jarvis-dependencies/     # Dependency management
├── jarvis-parent/          # Parent POM with plugin configurations
├── jarvis-modules/         # Core framework modules
├── jarvis-autoconfigure/   # Spring Boot auto-configuration
├── jarvis-starters/        # Spring Boot starters
├── jarvis-baseweb/         # Base web utilities
└── jarvis-dev-ops/         # Development operations tools
```

### Core Modules

#### jarvis-core
- Base entities and interfaces (`BaseEntity`, `BaseSimpleEntity`)
- Search and query abstractions (`Page`, `CriteriaQuery`)
- Utility classes and common exceptions
- Core security interfaces

#### jarvis-mybatis
- MyBatis integration and enhancements
- Dynamic entity operations
- Database function utilities
- Pagination support

#### jarvis-security
- Authentication and authorization framework
- Security utilities and configurations

#### jarvis-webmvc
- Web MVC configurations and utilities
- Exception handling
- Request/response processing

#### jarvis-oauth2-*
- OAuth2 authorization server
- OAuth2 resource server
- Common OAuth2 utilities

#### jarvis-openfeign
- OpenFeign integration
- Resilience4j circuit breaker support

### Key Architectural Patterns

#### Entity Hierarchy
- `BaseEntity<T>` - Root interface for all entities
- `BaseSimpleEntity` - Simple entity with ID
- `BaseDynamicEntity` - Dynamic entity support
- `BaseRevisionEntity` - Version-controlled entities

#### Search and Query
- `Page` - Pagination with summary support
- `CriteriaQuery` - Type-safe query builder
- `EntityQuery` - Entity-specific queries
- `DynamicEntityQuery` - Dynamic entity queries

#### Update Operations
- `EntityUpdate` - Entity update operations
- `CriteriaUpdate` - Criteria-based updates
- `DynamicEntityUpdate` - Dynamic entity updates

## Development Guidelines

### Entity Development
- Extend appropriate base entity classes
- Use `@IgnoreUpdate` for fields that shouldn't be updated
- Implement `CodeEnum` for enumerations
- Use `SerializableFunction` for type-safe property references

### Query Development
- Use `CriteriaQueryBuilder` for building queries
- Leverage `Page` for pagination with optional counting
- Use `OrderBy` for sorting
- Support dynamic entity queries through `DynamicEntityQuery`

### Exception Handling
- Use `BusinessException` for business logic errors
- Use `FrameworkException` for framework-level errors
- Follow consistent error message formatting

### Security Integration
- Implement `LoginUser` interface for user context
- Use security annotations for access control
- Leverage OAuth2 modules for authentication

## Configuration

### Database Support
- Oracle, Kingbase, Dameng, HighGo databases
- Multiple JDBC drivers included
- Hibernate dialects for different databases

### Framework Properties
- Configuration properties managed through Spring Boot
- Custom configuration processors for IDE support
- Environment-specific configurations

## Testing

### Test Configuration
- Tests run with JUnit 4
- JaCoCo coverage reporting enabled
- Test naming convention: *Test.java
- Memory configuration: -Xmx256M

### Test Execution
- Run all tests: `mvn test`
- Run specific test: `mvn test -Dtest=ClassName`
- Skip tests: `-Dmaven.test.skip=true`

## Deployment

### Docker Support
- Docker Maven plugin configured
- Automatic image tagging
- Dockerfile location: `src/main/docker`

### Artifact Publishing
- Custom Maven repository configuration
- Release and snapshot repositories
- Automatic source and javadoc attachment

## Code Quality

### Code Standards
- UTF-8 encoding enforced
- Checkstyle validation
- SpotBugs static analysis
- JaCoCo minimum coverage requirements

### Development Workflow
1. Build with `mvn clean install`
2. Run tests to ensure functionality
3. Check code quality reports
4. Verify coverage metrics
5. Package for deployment

## Important Notes

- The `jarvis-autoconfigure` module uses code obfuscation (Allatori)
- Multiple database vendors supported with custom dialects
- Framework designed for enterprise-level applications
- Strong emphasis on type safety and code quality
- Comprehensive security and OAuth2 support