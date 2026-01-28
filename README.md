# gaia_leave

- Employee Leave Management System

## Overview

GaiaLeave is a comprehensive employee leave management system designed to streamline the process of managing and tracking employee absences within a company. The system implements role-based access control, ensuring that HR personnel, managers, and employees have appropriate permissions based on their responsibilities.

## Technology Stack

### Core Platform
- **Java Version:** JDK 21
- **Framework:** Spring Boot 3.4.2
- **Build Tool:** Maven

### Database & Persistence

#### MariaDB Integration
- **Driver:** mariadb-java-client
- **Purpose:** Provides robust database connectivity
- **Features:**
    - High-performance JDBC driver
    - Comprehensive MariaDB support
    - Production-ready connection management

#### JPA & Hibernate
- **Package:** spring-boot-starter-data-jpa
- **Features:**
    - Simplified database operations via JPA
    - Hibernate ORM integration
    - Efficient repository management
    - Automated entity mapping

#### Database Migration
- **Tool:** Flyway (flyway-core, flyway-mysql)
- **Capabilities:**
    - Version-controlled database schema
    - Automated migration management
    - Environment-specific configurations
    - Consistent database state across deployments

### Web & API Layer

#### Web Framework
- **Package:** spring-boot-starter-web
- **Components:**
    - Embedded Tomcat server
    - Spring MVC framework
    - RESTful API support
    - HTTP request handling

#### Configuration Management
- **Tool:** spring-boot-configuration-processor
- **Benefits:**
    - Enhanced IDE support
    - Type-safe configuration properties
    - Automated metadata generation
    - Improved developer experience

### Security Framework

#### Core Security
- **Package:** spring-boot-starter-security
- **Features:**
    - Authentication mechanisms
    - Authorization controls
    - Session management
    - Security context handling

#### Testing Support
- **Package:** spring-security-test
- **Capabilities:**
    - Security integration testing
    - Authentication simulation
    - Authorization testing
    - Security context mocking

#### JWT Implementation
- **Package:** jjwt-api (version 0.12.6)
- **Features:**
    - Token-based authentication
    - Secure session management
    - JWT generation and validation
    - Customizable claims handling

### Monitoring & Operations

#### Application Monitoring
- **Package:** spring-boot-starter-actuator
- **Capabilities:**
    - Health check endpoints
    - Metrics collection
    - Performance monitoring
    - Production readiness features

### Validation & Testing

#### Data Validation
- **Package:** spring-boot-starter-validation
- **Features:**
    - Bean validation support
    - Custom validation rules
    - Annotation-based validation
    - Error handling

#### Testing Framework
- **Package:** spring-boot-starter-test
- **Components:**
    - JUnit support
    - Mockito integration
    - Spring test utilities
    - Assertion libraries

## Project Structure

```
|   .gitattributes
|   .gitignore
|   mvnw
|   mvnw.cmd
|   pom.xml
|   README.md
|
+---.idea
|       .gitignore
|       compiler.xml
|       encodings.xml
|       git_toolbox_blame.xml
|       git_toolbox_prj.xml
|       jarRepositories.xml
|       misc.xml
|       vcs.xml
|       workspace.xml
|
+---.mvn
|   \---wrapper
|           maven-wrapper.properties
|
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---caci
|   |   |           \---discipuli
|   |   |               \---GaiaLeave
|   |   |                   |   GaiaLeaveApplication.java
|   |   |                   |
|   |   |                   +---administration
|   |   |                   |   +---controller
|   |   |                   |   |       EndpointController.java
|   |   |                   |   |       RoleController.java
|   |   |                   |   |
|   |   |                   |   +---model
|   |   |                   |   |   +---request
|   |   |                   |   |   |       Endpoint.java
|   |   |                   |   |   |       JobPosition.java
|   |   |                   |   |   |       Menu.java
|   |   |                   |   |   |       Role.java
|   |   |                   |   |   |       RoleEndpoint.java
|   |   |                   |   |   |       RoleMenu.java
|   |   |                   |   |   |       User.java
|   |   |                   |   |   |
|   |   |                   |   |   \---response
|   |   |                   |   |           EndpointResponse.java
|   |   |                   |   |           JobPositionResponse.java
|   |   |                   |   |           MenuResponse.java
|   |   |                   |   |           RoleEndpointResponse.java
|   |   |                   |   |           RoleMenuResponse.java
|   |   |                   |   |           RoleResponse.java
|   |   |                   |   |           UserResponse.java
|   |   |                   |   |
|   |   |                   |   +---repository
|   |   |                   |   |   +---request
|   |   |                   |   |   |       EndpointRepository.java
|   |   |                   |   |   |       JobPositionRepository.java
|   |   |                   |   |   |       MenuRepository.java
|   |   |                   |   |   |       RoleEndpointRepository.java
|   |   |                   |   |   |       RoleMenuRepository.java
|   |   |                   |   |   |       RoleRepository.java
|   |   |                   |   |   |       UserRepository.java
|   |   |                   |   |   |
|   |   |                   |   |   \---response
|   |   |                   |   |           EndpointResponseRepository.java
|   |   |                   |   |           JobPositionResponseRepository.java
|   |   |                   |   |           MenuResponseRepository.java
|   |   |                   |   |           RoleEndpointResponseRepository.java
|   |   |                   |   |           RoleMenuResponseRepository.java
|   |   |                   |   |           RoleResponseRepository.java
|   |   |                   |   |           UserResponseRepository.java
|   |   |                   |   |
|   |   |                   |   \---service
|   |   |                   |           EndpointService.java
|   |   |                   |           RoleService.java
|   |   |                   |
|   |   |                   \---shared_tools
|   |   |                       +---component
|   |   |                       |       AfterStartUp.java
|   |   |                       |       ScheduledTasks.java
|   |   |                       |
|   |   |                       +---configuration
|   |   |                       |       AppProperties.java
|   |   |                       |       JpaAuditingConfig.java
|   |   |                       |       RestTemplateConfig.java
|   |   |                       |       SecurityConfiguration.java
|   |   |                       |
|   |   |                       +---exception
|   |   |                       |       CustomAdvice.java
|   |   |                       |       CustomException.java
|   |   |                       |       CustomGlobalExceptionHandler.java
|   |   |                       |       CustomGlobalExceptionHandlerIpl.java
|   |   |                       |       ErrorModel.java
|   |   |                       |
|   |   |                       +---helper
|   |   |                       |       AllHelpers.java
|   |   |                       |
|   |   |                       +---model
|   |   |                       |       Auditable.java
|   |   |                       |       Mapping.java
|   |   |                       |       MappingServicesWrapper.java
|   |   |                       |
|   |   |                       \---service
|   |   |                               MappingService.java
|   |   |
|   |   \---resources
|   |       |   application.properties
|   |       |
|   |       \---db
|   |           \---migration
|   |                   V1_0__init.sql
|   |                   V2_0__role.sql
|   |                   V3_0__menu.sql
|   |                   V4_0__endpoint.sql
|   |                   V5_0__role_menu.sql
|   |                   V6_0__role_endpoint.sql
|   |                   V7_0__job_position.sql
|   |                   V8_0__user.sql
|   |
|   \---test
|       \---java
|           \---com
|               \---caci
|                   \---discipuli
|                       \---GaiaLeave
|                               GaiaLeaveApplicationTests.java
|
\---target
    +---classes
    |   |   application.properties
    |   |
    |   +---com
    |   |   \---caci
    |   |       \---discipuli
    |   |           \---GaiaLeave
    |   |               |   GaiaLeaveApplication.class
    |   |               |
    |   |               +---administration
    |   |               |   +---controller
    |   |               |   |       EndpointController.class
    |   |               |   |       RoleController.class
    |   |               |   |
    |   |               |   +---model
    |   |               |   |   +---request
    |   |               |   |   |       Endpoint$EndpointBuilder.class
    |   |               |   |   |       Endpoint.class
    |   |               |   |   |       JobPosition.class
    |   |               |   |   |       Menu.class
    |   |               |   |   |       Role.class
    |   |               |   |   |       RoleEndpoint.class
    |   |               |   |   |       RoleMenu.class
    |   |               |   |   |       User.class
    |   |               |   |   |
    |   |               |   |   \---response
    |   |               |   |           EndpointResponse.class
    |   |               |   |           JobPositionResponse.class
    |   |               |   |           MenuResponse.class
    |   |               |   |           RoleEndpointResponse.class
    |   |               |   |           RoleMenuResponse.class
    |   |               |   |           RoleResponse.class
    |   |               |   |           UserResponse.class
    |   |               |   |
    |   |               |   +---repository
    |   |               |   |   +---request
    |   |               |   |   |       EndpointRepository.class
    |   |               |   |   |       JobPositionRepository.class
    |   |               |   |   |       MenuRepository.class
    |   |               |   |   |       RoleEndpointRepository.class
    |   |               |   |   |       RoleMenuRepository.class
    |   |               |   |   |       RoleRepository.class
    |   |               |   |   |       UserRepository.class
    |   |               |   |   |
    |   |               |   |   \---response
    |   |               |   |           EndpointResponseRepository.class
    |   |               |   |           JobPositionResponseRepository.class
    |   |               |   |           MenuResponseRepository.class
    |   |               |   |           RoleEndpointResponseRepository.class
    |   |               |   |           RoleMenuResponseRepository.class
    |   |               |   |           RoleResponseRepository.class
    |   |               |   |           UserResponseRepository.class
    |   |               |   |
    |   |               |   \---service
    |   |               |           EndpointService.class
    |   |               |           RoleService.class
    |   |               |
    |   |               \---shared_tools
    |   |                   +---component
    |   |                   |       AfterStartUp.class
    |   |                   |       ScheduledTasks.class
    |   |                   |
    |   |                   +---configuration
    |   |                   |       AppProperties.class
    |   |                   |       JpaAuditingConfig.class
    |   |                   |       RestTemplateConfig.class
    |   |                   |       SecurityConfiguration.class
    |   |                   |
    |   |                   +---exception
    |   |                   |       CustomAdvice.class
    |   |                   |       CustomException.class
    |   |                   |       CustomGlobalExceptionHandler.class
    |   |                   |       CustomGlobalExceptionHandlerIpl.class
    |   |                   |       ErrorModel.class
    |   |                   |
    |   |                   +---helper
    |   |                   |       AllHelpers.class
    |   |                   |
    |   |                   +---model
    |   |                   |       Auditable.class
    |   |                   |       Mapping.class
    |   |                   |       MappingServicesWrapper.class
    |   |                   |
    |   |                   \---service
    |   |                           MappingService.class
    |   |
    |   \---db
    |       \---migration
    |               V1_0__init.sql
    |               V2_0__role.sql
    |               V3_0__menu.sql
    |               V4_0__endpoint.sql
    |               V5_0__role_menu.sql
    |               V6_0__role_endpoint.sql
    |               V7_0__job_position.sql
    |               V8_0__user.sql
    |
    \---generated-sources
        \---annotations
```

## Getting Started

1. Ensure you have JDK 21 installed
2. Clone the repository
3. Configure database properties in `application.properties`
4. Run `mvn clean install`
5. Start the application using `mvn spring-boot:run`

## Key Features

- Role-based access control
- Leave request management
- Approval workflow
- Absence tracking
- Reporting capabilities
- Security audit logging


# 1.Database Migrations (Flyway)

This project uses **Flyway** for database versioning. Each migration is applied automatically when the application starts.

## Migration Structure
Migration scripts are located in the `src/main/resources/db/migration/` folder and follow this convention:
- `V{number}__{name}.sql` (e.g., `V1_0__init.sql`)

### Migration Overview:
1. **V1_0__init.sql** - Creates the basic tables.
2. **V2_0__role.sql** - Creates the `role` table with different user roles.
3. **V3_0__menu.sql** - Creates the `menu` table with menu items.
4. **V4_0__endpoint.sql** - Creates the `endpoint` table, which defines API endpoints.
5. **V5_0__role_menu.sql** - Links roles with menu items.
6. **V6_0__role_endpoint.sql** - Links roles with API endpoints.
7. **V7_0__job_position.sql** - Creates the `job_position` table with job positions.
8. **V8_0__user.sql** - Creates the `user` table with user data.

### 🔐 Environment Variables

Sensitive values are not stored directly in `application.properties`.  
Instead, they are injected via environment variables or a `.env` file (excluded from GitHub).

Required variables:

```env
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=gaia_leave
DB_USER=your_db_user
DB_PASS=your_db_password

# Spring Security
SECURITY_USER=your_admin_user
SECURITY_PASS=your_admin_password

# JWT
JWT_SECRET=your_jwt_secret

# Mail
MAIL_USER=your_mailjet_user
MAIL_PASS=your_mailjet_password
MAIL_FROM=your_email@example.com

# Other
TIMEZONE=Europe/Belgrade
```

## Contributing

Please read our contributing guidelines before submitting pull requests.

---

*Developed and maintained by ĆACI*
