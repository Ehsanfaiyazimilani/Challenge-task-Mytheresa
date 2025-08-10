# 🛍️  Challenge for Backend - Mytheresa - Product Discount API

This project is a solution to a coding challenge that requires designing a simple yet scalable API to return a list of discounted products. Built with **Java 17** and **Spring Boot**, it uses an in-memory JSON-based data source and follows clean architecture principles for maintainability and testability.

[![Java CI with Maven](https://github.com/Ehsanfaiyazimilani/Challenge-task-Mytheresa/actions/workflows/maven.yml/badge.svg)](https://github.com/Ehsanfaiyazimilani/Challenge-task-Mytheresa/actions/workflows/maven.yml)

## 🚀 Features

- ✅ Single endpoint: `GET /products`
- ✅ Filter by `category`
- ✅ Optional filter by `priceLessThan` (applies before discount)
- ✅ Handles conflicting discounts — the highest discount is applied
- ✅ In-memory storage, supports up to 20,000+ products
- ✅ Modular architecture using strategy pattern for discount logic
- ✅ Fully tested with JUnit

---

## 🧠 Business Rules

- Products in the **"boots"** category get a **30% discount**
- Product with **SKU = 000003** gets a **15% discount**
- If multiple discounts apply, the **greater one wins**

---

## 🔍 API Usage

### Endpoint

GET /products

### Query Parameters

| Parameter        | Type   | Description                                 |
|------------------|--------|---------------------------------------------|
| `category`        | String | Filter products by category                 |
| `priceLessThan`   | Int    | Filter products by price before discount    |

### Response Format

Each product is returned in the following format:

```json
{
  "sku": "000001",
  "name": "BV Lean leather ankle boots",
  "category": "boots",
  "price": {
    "original": 89000,
    "final": 62300,
    "discount_percentage": "30%",
    "currency": "EUR"
  }
}

If no discount is applied:
{
  "sku": "000005",
  "name": "Nathane leather sneakers",
  "category": "sneakers",
  "price": {
    "original": 59000,
    "final": 59000,
    "discount_percentage": null,
    "currency": "EUR"
  }
}
```
## ⚙️ Run the Project
Make sure you have Java 17+ and Maven installed.
```
mvn spring-boot:run
```
## 🧪 Run Tests
```
mvn test
```
All tests are written using pure in-memory logic — no need for networking or filesystem access.
## 📚 Viewing Documentation

The project documentation is generated as HTML files located in the `target/site/apidocs` folder. To view the documentation, follow these steps:

1. Open the `index.html` file located inside `target/site/apidocs`.
2. You can open this file with any web browser (e.g., Chrome, Firefox) to see the full project documentation.

> **Note:**  
> If you want to open it from the command line, you can use the following commands:

On Windows:

```bash
start target/site/apidocs/index.html
```
On Linux / macOS:
```bash
open target/site/apidocs/index.html
```
## 🧱 Architecture Overview
Controller Layer – exposes the API

Service Layer – handles business logic and filtering

Discount Rules – applied via a DiscountRule strategy interface

Configuration Layer – loads products from JSON into memory

Model Layer – uses modern Java record types for clarity

## 🧠 Design Decisions
In-memory JSON was chosen for simplicity and portability.

Discounts are implemented using a DiscountRule strategy pattern for extensibility.

Filtering and discounting are completely decoupled for clean separation of concerns.

No frameworks like Lombok are used to keep the code explicit and clear.

📁 Project Structure
```
src
├── main
│   ├── java/com/example/productapi
│   │   ├── controller          # REST API endpoints
│   │   ├── service             # Business logic
│   │   ├── model               # Data models (e.g., Product, Discount)
│   │   ├── data                # In-memory data source (e.g., JSON files)
│   │   └── exception           # Custom exceptions and error handling
│   └── resources               # Configuration and static files
├── test
│   └── java/com/example/productapi
│       ├── controller          # Unit tests for controllers
│       ├── service             # Unit tests for services
│       └── exception           # Unit tests for exception handling
└── target                      # Compiled classes and build artifacts
    └── site
        └── apidocs             # Generated JavaDoc documentation


```
🧊 Example Requests

# Get all products
curl http://localhost:8080/products

# Get boots only
curl http://localhost:8080/products?category=boots

# Get products with original price <= 80000
curl http://localhost:8080/products?priceLessThan=80000

