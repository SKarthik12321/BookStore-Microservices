# Bookstore Microservices

## Tech Stack
- Java 17 + Spring Boot 3.2.5
- Spring Cloud 2023.0.1 (Eureka, Gateway, Config, Feign)
- PostgreSQL 16 (per service)
- Redis 7 (Cart service)
- Apache Kafka (async events)
- JWT (Spring Security)
- SpringDoc OpenAPI 3 / Swagger UI
- Docker + Docker Compose

## Services & Ports
| Service | Port | DB |
|---|---|---|
| API Gateway | 8080 | — |
| Eureka | 8761 | — |
| Config Server | 8888 | — |
| User Service | 8081 | user_db |
| Admin Service | 8082 | admin_db |
| Product Service | 8083 | product_db |
| Cart Service | 8084 | Redis |
| Wishlist Service | 8085 | wishlist_db |
| Customer Service | 8086 | customer_db |
| Order Service | 8087 | order_db |
| Feedback Service | 8088 | feedback_db |
| Notification Service | 8089 | — |

## Prerequisites
- Java 17+
- Maven 3.8+
- Docker Desktop (optional, for Docker run)
- IntelliJ IDEA (recommended)

## IntelliJ Setup — Dependencies to Install as Plugins
1. **Lombok Plugin** → Settings > Plugins > search "Lombok" → Install
2. Enable annotation processing: Settings > Build > Compiler > Annotation Processors > ✅ Enable

## Option A: Run with Docker Compose (Easiest)
```bash
cd infrastructure
docker-compose up --build
```

## Option B: Run Locally (requires PostgreSQL + Redis + Kafka running)

### 1. Create PostgreSQL databases
```sql
CREATE DATABASE user_db;
CREATE DATABASE admin_db;
CREATE DATABASE product_db;
CREATE DATABASE wishlist_db;
CREATE DATABASE customer_db;
CREATE DATABASE order_db;
CREATE DATABASE feedback_db;
```

### 2. Start services in order
```bash
# Terminal 1 — Eureka
cd eureka-server && mvn spring-boot:run

# Terminal 2 — Config Server
cd config-server && mvn spring-boot:run

# Terminal 3 — API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 4..12 — Each microservice
cd services/user-service && mvn spring-boot:run
cd services/admin-service && mvn spring-boot:run
cd services/product-service && mvn spring-boot:run
cd services/cart-service && mvn spring-boot:run
cd services/wishlist-service && mvn spring-boot:run
cd services/customer-service && mvn spring-boot:run
cd services/order-service && mvn spring-boot:run
cd services/feedback-service && mvn spring-boot:run
cd services/notification-service && mvn spring-boot:run
```

## Swagger UI URLs
| Service | URL |
|---|---|
| User | http://localhost:8081/swagger-ui.html |
| Product | http://localhost:8083/swagger-ui.html |
| Cart | http://localhost:8084/swagger-ui.html |
| Wishlist | http://localhost:8085/swagger-ui.html |
| Customer | http://localhost:8086/swagger-ui.html |
| Order | http://localhost:8087/swagger-ui.html |
| Feedback | http://localhost:8088/swagger-ui.html |

## API Quick Test
```bash
# 1. Register
curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@test.com","password":"Password1"}'

# 2. Login → copy token
curl -X POST http://localhost:8081/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"Password1"}'

# 3. Use token
curl http://localhost:8081/api/users/profile \
  -H "Authorization: Bearer <TOKEN>"
```

## Branching Strategy
```
main          ← production
develop       ← integration
feature/user-service
feature/product-service
feature/order-service
hotfix/...
```

## Git Setup Commands
```bash
git init
git remote add origin https://github.com/YOUR_USERNAME/bookstore-microservices.git
git checkout -b develop
git add .
git commit -m "Initial project setup — all microservices"
git push -u origin develop
```