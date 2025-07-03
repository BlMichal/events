# Use Eclipse Temurin (Java 21) base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the built Spring Boot JAR file
COPY target/*.jar app.jar

# Expose port (optional, handled in docker-compose)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]