# Line of reasoning

### Application Architecture

The API follows a **layered architecture** to ensure separation of concerns, maintainability, and scalability.

### **Architecture Diagram**

```
           +----------------------+
           |  WireMock (Fake API)  |
           |  (Simulated Data)     |
           +----------+-----------+
                      |
                      v
           +----------------------+
           |  QikServe Checkout   |
           |     Spring Boot API  |
           +----------------------+
             |        |       |
             |        |       |
             v        v       v
  +----------------+   +----------------+   +----------------+
  |  Controller    |   |  Service       |   |  Repository    |
  |  (Handles API  |   |  (Business     |   |  (Fetches      |
  |   Requests)    |   |  Logic)        |   |  WireMock Data)|
  +----------------+   +----------------+   +----------------+
                      |
                      v
          +----------------------------+
          |       Database (Optional)   |
          +----------------------------+

```

- **Controller Layer**: Exposes REST endpoints.
- **Service Layer**: Contains business logic, including promotion application.
- **Repository Layer**: Fetches data from WireMock.

## **Checkout Flow**

The checkout process follows these key steps:

```
1. The customer sends a JSON request with cart items to /checkout.
2. The API queries WireMock to retrieve product details.
3. The checkout service applies promotions and calculates the total.
4. The response is sent back to the customer, including the total and savings.

```

Sequence Diagram

```
Client          API          WireMock
   |             |              |
   | POST /checkout |              |
   |------------>|              |
   |             | GET /products/{id} |
   |             |-------------->|  
   |             | Product + Promotion |  
   |             |<--------------|  
   |             | Computes total and savings |
   |             | Sends JSON response |
   |<------------|  

```

## **Data Structure**

The API uses the following models to represent products, promotions, and the cart.

### **Product Model (`Product.java`)**

```java

public class Product {
    private String id;
    private String name;
    private int price; // In cents
    private List<Promotion> promotions;
}

```

### **Promotion Model (`Promotion.java`)**

```java
public class Promotion {
    private String type;
    private int requiredQty;
    private int freeQty;
    private int amount;
    private int price;
}

```

### **Cart Model (`CartDTO.java`)**

```json
{
  "items": [
    {
      "productId": "PWWe3w1SDU",
      "quantity": 3
    }
  ]
}

```

---

## **Technical Decisions**

### **Use of WireMock**

- Instead of a real database, the API **fetches products and promotions from WireMock**, simulating an external POS system.

### **Resilience4j for Fault Tolerance**

- If WireMock becomes unavailable, the API could implement **fallback mechanisms** using `Resilience4j` to ensure reliability.

### **Internationalization**

- The API supports **multiple languages**, dynamically returning messages in English or Portuguese based on the `Accept-Language` header.