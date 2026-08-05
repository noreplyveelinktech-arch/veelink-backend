# Veelink CMS Backend

Spring Boot 3.2.x backend for a generic IT-training-institute CMS.

## Prerequisites
- Java 17+
- Maven 3.9+ (or use the included `mvnw.cmd` wrapper once generated)
- PostgreSQL

## Run locally
1. Set environment variables from `.env.example` or update `src/main/resources/application.yml` for local use.
2. Start the app:
   ```bash
   mvn spring-boot:run
   ```
   If Maven is not installed system-wide, use:
   ```bash
   mvnw.cmd spring-boot:run
   ```

## Seeded admin login
- Email: `admin@veelinktechnologies.com`
- Password: `Admin@123`

## Build
```bash
mvn clean package
```
The application jar is generated in `backend/target/*.jar`.