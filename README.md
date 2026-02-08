
- **Spring Boot 3.x** - Application framework
- **Spring Security** - Authentication and authorization
- **JWT** - Token-based authentication
- **MySQL** - Database
- **JPA/Hibernate** - ORM
- **Maven** - Dependency management

- JWT-based authentication
- Role-based access control (PARENT/ADMIN)
- Automated vaccination reminders
- RESTful API endpoints
- Activity logging

- - User login
- POST /api/auth/login - User login
- POST /api/auth/register - User registration
- POST /api/auth/register - User registration

- GET /api/parent/children - Get all children
- POST /api/parent/children - Add new child
- GET /api/parent/notifications - Get notifications
- GET /api/parent/vaccination-records - Get vaccination records

- GET /api/admin/dashboard/stats - Dashboard statistics
- GET /api/admin/users - Manage users
- - Generate notifications
- POST /api/admin/notifications/generate-reminders - Generate notifications


Update src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/TG_DB
spring.datasource.username=root
spring.datasource.password=yourpassword




Application will start on:
Application will start on: http://localhost:8080/api
