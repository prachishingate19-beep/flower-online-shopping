# Flower Online Shopping Website

A complete Java Spring Boot e-commerce backend for a flower shop with user authentication, product catalog, shopping cart, order management, secure REST APIs, and MySQL persistence.

## Features
- User registration and login with Spring Security + JWT
- Role-based access control (`ROLE_USER`, `ROLE_ADMIN`)
- Flower catalog with search, category, occasion, and price filtering
- Shopping cart with add, update, and remove operations
- Order creation with payment method and shipping address
- Admin dashboard to manage flowers, stock, and orders
- MySQL integration using Spring Data JPA
- REST API documentation with Swagger UI

## Tech Stack
- Java 17
- Spring Boot 3.3.x
- Spring Security
- Spring Data JPA
- MySQL 8
- JWT Authentication
- Swagger/OpenAPI

## Project Structure
- `controller` — REST endpoints
- `service` — business logic
- `repository` — database access
- `entity` — JPA entities
- `dto` — request/response models
- `security` — JWT and auth configuration
- `config` — app configuration

## Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8 or Docker Desktop

## Run with Docker MySQL
```bash
docker compose up -d
```

## Start the application
```bash
mvn clean install
mvn spring-boot:run
```

The application will run on:
- http://localhost:8080

## Swagger UI
- http://localhost:8080/swagger-ui.html

## Default API Overview
### Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`

### Flowers
- `GET /api/flowers`
- `GET /api/flowers/{id}`
- `GET /api/flowers/search`

### Cart
- `GET /api/cart`
- `POST /api/cart/add`
- `PUT /api/cart/{flowerId}`
- `DELETE /api/cart/{flowerId}`

### Orders
- `POST /api/orders`
- `GET /api/orders`
- `GET /api/orders/{id}`

### Admin
- `POST /api/admin/flowers`
- `PUT /api/admin/flowers/{id}`
- `DELETE /api/admin/flowers/{id}`
- `GET /api/admin/orders`

## Sample JSON Examples
### Register
```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "password": "Pass@123",
  "role": "ROLE_USER"
}
```

### Login
```json
{
  "email": "alice@example.com",
  "password": "Pass@123"
}
```

### Add Flower
```json
{
  "name": "Rose Bouquet",
  "description": "Elegant red rose bouquet for special occasions.",
  "category": "Bouquet",
  "occasion": "Anniversary",
  "price": 49.99,
  "stock": 25,
  "imageUrl": "https://images.example.com/rose-bouquet.jpg"
}
```

### Add to Cart
```json
{
  "flowerId": 1,
  "quantity": 2
}
```

### Place Order
```json
{
  "paymentMethod": "CASH_ON_DELIVERY",
  "shippingAddress": "123 Garden Street, Pune, India"
}
```

## Security Notes
- JWT tokens should be passed in the Authorization header as: `Bearer <token>`
- For production, change the JWT secret and use a stronger database password
- Validate all inputs and enable proper firewall and HTTPS in deployment

## Credits
Built as a complete online flower shopping backend for a real-world florist store with REST API-first architecture.
