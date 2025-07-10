# 📦 Ecommerce API

Uma API RESTful desenvolvida com **Spring Boot** para gerenciar um sistema de **e-commerce**, incluindo funcionalidades como cadastro de produtos, gerenciamento de estoque, usuários, carrinho de compras, pedidos e descontos.

## 🚀 Tecnologias Utilizadas

 - Java 21 
 - Spring Boot 3
 - Spring Data JPA
 - PostgresSQL
 - Spring Security
 - JWT (JJWT)
 - OAuth2 Resource Server
 - Spring AMQP + RabbitMQ
 - Spring Mail
 - Spring Validation
 - MapStruct 
 - Thymeleaf
 - Lombok
 - Docker & Docker Compose

## 📁 Estrutura Inicial do Projeto

    src/
        ├── config/
        │
        ├── controller/
        │   │
        │   └── common/
        │
        ├── enum/
        │
        ├── domain/
        │
        ├── dto/
        │   │
        │   └── request/
        │   │
        │   └── response/
        │
        ├── exception/
        │
        ├── mapper/
        │
        ├── repository/
        │
        ├── security/
        │
        ├── service/
        │   │
        │   └── impl/
        │
        ├── util/
        │
        ├── validation/
        │
        ├── Application.java

## ⚙️ Como executar o projeto

### Pré-requisitos
  - Java 21
  - Maven 3.8
  - Docker

### Passos

#### 1. Clone o repositório:

```bash
  git clone https://github.com/seuusuario/ecommerce-api.git
  cd ecommerce-api
```

#### 2. Configure o **application.properties**:
    
    Defina as variaveis de ambientes:

    - DB_URL
    - DB_DATABASE
    - DB_USER
    - DB_PASS
    - MQ_USER
    - MQ_PASS
    - PG_EMAIL
    - PG_PASS
    - MINIO_USER
    - MINIO_PASS
    - JWT_SECRET
    - JWT_DURATION
    - CLIENT_ID
    - CLIENT_SECRET
    - MAIL_USER
    - MAIL_PASS
    - FRONT_URL

#### 3. Execute o docker compose

```bash
  docker-compose up -d
```

#### 4. Execute a API:

```bash
  mnv clean install -DskipTests
  mvn spring-boot:run
```

## 📄 Licença

Distribuído sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.