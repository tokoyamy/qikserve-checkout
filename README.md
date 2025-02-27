# Checkout APIt

**Description**
The QikServe Checkout API is a system that processes checkout orders for a supermarket, consuming data from WireMock to retrieve products and promotions. The API automatically applies discounts and returns the total order value and savings to the customer.

**Tecnologias Utilizadas**

- **Java 17+**
- **Spring Boot 3**
- **Spring Web**
- **Spring Boot Actuator**
- **Spring Validation**
- **WireMock**
- **JUnit 5 e Mockito**
- **Lombok**
- **SLF4J + Logback**
- **Swagger/OpenAPI**

### How to Run the Project

***Prerequisites***
Before starting, make sure you have the following software installed:

- Java 17+
- Maven
- WireMock (for data simulation)

Clone the Repository

```bash

git clone git@github.com:tokoyamy/qikserve-checkout.git
cd qikserve-checkout

```

### Launch WireMock

run the startup script
Link to wiremock: https://cdn-dev.preoday.com/test/mock-api.zip

```bash
bash start.sh

```

## Available Endpoints

### Products

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/products` | Return all products |
| `GET` | `/products/{id}` | Returns a product by ID |

### **Checkout**

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/checkout` | Processes the checkout and returns the customer's total and savings |
| `POST` | `/checkout/detailed` | Returns checkout details with promotions applied |

---

## Request Example

### ***Checkout***

Request:

```bash
curl --location 'http://localhost:8080/checkout' \
--header 'Content-Type: application/json' \
--data '{
          "items": [
            {
              "productId": "PWWe3w1SDU",
              "quantity": 3
            },
            {
              "productId": "Dwt5F7KAhi",
              "quantity": 2
            },
            {
              "productId": "C8GDyLrHJb",
              "quantity": 1
            }
          ]
        }'

```

### **Response**

```json
{
    "total": 5645,
    "savings": 49
}

```
