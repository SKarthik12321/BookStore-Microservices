# Bookstore E-Commerce Microservices

**Production-Ready Monorepo Architecture**  
**Spring Boot 3.x + Spring Cloud** | Java 17+

A complete e-commerce backend for a bookstore, built as **9 independent microservices** following industry best practices. The system replaces a monolithic backend with a scalable, maintainable microservices architecture using Spring Cloud, Docker, and modern DevOps patterns.

## 🏗️ System Architecture

The monolithic Bookstore Back-end API has been decomposed into **9 independent microservices**. Each service:
- Owns its own database (Database-per-Service pattern)
- Exposes RESTful APIs
- Communicates via HTTP (synchronous) or Kafka (asynchronous)
- All external traffic routes through a single **API Gateway**

### Core Infrastructure Services
| Component              | Port   | Technology                          | Responsibility |
|------------------------|--------|-------------------------------------|----------------|
| **API Gateway**        | 8080   | Spring Cloud Gateway                | Routing, JWT validation, rate limiting, CORS |
| **Eureka Server**      | 8761   | Netflix Eureka (Spring Cloud)       | Service discovery & registration |
| **Config Server**      | 8888   | Spring Cloud Config Server          | Centralized configuration management |

### Microservices Overview
| # | Microservice              | Port | Database          | Primary Responsibility |
|---|---------------------------|------|-------------------|------------------------|
| 1 | **User Service**          | 8081 | PostgreSQL        | Registration, login, JWT issuance, profile management |
| 2 | **Admin Service**         | 8082 | PostgreSQL        | Admin management, role assignment, dashboard |
| 3 | **Product Service**       | 8083 | PostgreSQL        | CRUD for books, categories, inventory |
| 4 | **Cart Service**          | 8084 | Redis             | Shopping cart sessions & calculations |
| 5 | **WishList Service**      | 8085 | PostgreSQL        | User-specific wish-list management |
| 6 | **Customer Service**      | 8086 | PostgreSQL        | Delivery addresses, preferences |
| 7 | **Order Service**         | 8087 | PostgreSQL        | Order lifecycle, status tracking, Kafka events |
| 8 | **Feedback Service**      | 8088 | PostgreSQL        | Product reviews & ratings |
| 9 | **Notification Service**  | 8089 | —                 | Event-driven emails/SMS via Kafka |

## 🛠️ Technologies Used

- **Language/Framework**: Java 17+ / Spring Boot 3.x
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Config Management**: Spring Cloud Config Server
- **Security**: Spring Security + JWT
- **Messaging**: Apache Kafka
- **Databases**: PostgreSQL (per service) + Redis (cache)
- **Documentation**: SpringDoc OpenAPI 3 / Swagger UI
- **Containerization**: Docker + Docker Compose
- **Orchestration**: Kubernetes (optional)
- **Build Tool**: Maven (Parent POM)

## 📂 Project Structure (Monorepo)

```bash
bookstore-microservices/
│
├── infrastructure/                # Infra-related configs
│   ├── docker-compose.yml
│   ├── k8s/                       # Kubernetes YAMLs
│   └── scripts/
│
├── config-server/
├── eureka-server/
├── api-gateway/
│
├── services/                      # All business microservices
│   ├── user-service/
│   ├── admin-service/
│   ├── product-service/
│   ├── cart-service/
│   ├── wishlist-service/
│   ├── customer-service/
│   ├── order-service/
│   ├── feedback-service/
│   └── notification-service/
│
├── common-lib/                    # Shared code (VERY IMPORTANT)
│   ├── dto/
│   ├── utils/
│   ├── exception/
│   └── security/
│
├── docs/
│   └── architecture.md
│
├── .github/workflows/             # CI/CD pipelines
├── pom.xml                        # Parent Maven POM
└── README.md
```
## 🚀 Deployment & Documentation
Containerization: Every service includes a Dockerfile
Orchestration: Docker Compose (development) + Kubernetes (production)
API Documentation: Swagger UI available on every service and aggregated at the API Gateway
Shared Library: common-lib contains reusable DTOs, utilities, exceptions, and security logic
S.Karthik
