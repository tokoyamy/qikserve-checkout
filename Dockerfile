FROM openjdk:17
WORKDIR /app
COPY target/qikserve-checkout.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
