# Follow up questions

## **1. How long did you spend on the test? What would you add if you had more time?**

The total time spent on the test was approximately **2 hours and 20 minutes**.

If I had more time, I would add:

- More automated tests, including integration tests to validate the complete checkout flow.
- Security improvements, such as authentication and authorization to protect endpoints.
- Caching to reduce calls to WireMock and improve performance.
- Monitoring and metrics using Actuator to track usage statistics.

---

## **2. What was the most useful feature added in the latest version of your chosen language? Please include a snippet of code that demonstrates its use.**

One of the most useful improvements in **Java 17+** is **Pattern Matching for switch expressions**, which allows handling different types without needing multiple `instanceof` checks and manual casting.

### **Example applied to the checkout system**

In the project code, we could improve promotion application by using this feature to make the code cleaner and more secure.

📂 **File:** `CheckoutService.java`

```java
    private int applyPromotions(CartItem item) {
        int price = item.getProduct().getPrice();
        int quantity = item.getQuantity();

        for (Promotion promotion : item.getProduct().getPromotions()) {
            switch (promotion.getType()) {
                case "BUY_X_GET_Y_FREE":
                    if (promotion.getRequiredQty() > 0) {
                        int freeItems = (quantity / promotion.getRequiredQty()) * promotion.getFreeQty();
                        quantity -= freeItems;
                    }
                    break;
                case "QTY_BASED_PRICE_OVERRIDE":
                    if (promotion.getRequiredQty() > 0) {
                        int bundles = quantity / promotion.getRequiredQty();
                        int remaining = quantity % promotion.getRequiredQty();
                        price = (bundles * promotion.getPrice()) + (remaining * price);
                    }
                    break;
                case "FLAT_PERCENT":
                    if (promotion.getAmount() > 0) {
                        price = price - (price * promotion.getAmount() / 100);
                    }
                    break;
                default:
                    break;
            }
        }

        return price * quantity;
    }
}

```

---

### **Benefits of Pattern Matching for Switch**

- **More concise and readable code**, eliminating the need for multiple `if-else` statements.
- **Prevents `NullPointerException` errors**, as `switch` can handle `null` cases.
- **Improves code safety** by using `when` conditions before executing logic.
- **Replaces the need for `instanceof` checks followed by manual casting**, making the code more efficient.

---

## **3. What did you find most difficult?**

The biggest challenges were:

- Ensuring correct promotion application since each product has a different type of promotion (e.g., `BUY_X_GET_Y_FREE`, `FLAT_PERCENT`), requiring a robust logic to handle them properly.
- Debugging zero values, as initially, promotions were not being applied correctly due to mismatched field names between WireMock and the Java models.
- Internationalization (i18n), which required configuring multi-language support and ensuring that messages were returned correctly in the expected format.

---

## **4. What mechanism did you implement to track issues in production on this code? If you didn’t implement anything, describe what you could do.**

Currently, the code uses **structured logging** via **SLF4J** and **Logback** to track errors and important events.

**Example in `CheckoutController`**

```java
logger.info("Receiving checkout request for {} items", cartDTO.getItems().size());
logger.error("Error processing checkout: {}", e.getMessage());

```

**What could be improved for production?**

- **Monitoring via Spring Boot Actuator** to expose API health metrics.
- **Integration with a centralized logging system**, such as ELK Stack (Elasticsearch + Logstash + Kibana) or Grafana.
- **Error alerts via Sentry or Prometheus** to capture exceptions in real time.
- **Distributed tracing with OpenTelemetry** to analyze WireMock calls and identify performance bottlenecks.

---

## **5. Explain your interpretation of the list of requirements and what was delivered or not delivered and why.**

The API was developed following the **MoSCoW prioritization**, ensuring that all mandatory requirements were correctly implemented and that the essential functionalities were available. Below is a detailed analysis of what was delivered and what could still be improved.

### **Mandatory Requirements (MUST HAVE)**

All mandatory requirements were fully met. The API was implemented in Java and consumes WireMock data to retrieve product and promotion information. Additionally, the checkout logic ensures that promotions are applied correctly.

Another mandatory requirement was the implementation of **automated tests**. Unit tests were developed to validate the correct functioning of price and promotion calculations, ensuring that the logic works as expected.

### **Desirable Requirements (SHOULD HAVE)**

Among the desirable requirements, the API was **prepared for internationalization**, allowing response messages to be adapted to the user's preferred language based on the `Accept-Language` header. This feature improves API accessibility and facilitates its use in different regions.

However, **the API has not yet been hosted**, meaning it is not available for external testing. This requirement can be easily met by deploying the application on a cloud service like **Heroku, AWS, or Railway**.

### **Optional Requirements (COULD HAVE)**

Multi-tenancy support for multiple WireMock instances, which would allow the API to interact with different WireMock setups representing multiple points of sale (POS), was **not implemented**. Currently, the API is configured to consume data from a **single WireMock instance**. To support multiple tenants, it would be necessary to parameterize the WireMock URL and allow dynamic configuration of the data endpoint.

### **What was not implemented (WONT HAVE)**

A **graphical interface** to consume the API was not implemented, as this requirement was **not necessary** for the test scope. Since the focus was on API development and the correct application of promotions, front-end implementation was **not prioritized**.
