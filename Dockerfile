# Use Maven image with OpenJDK 17 to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the Spring Boot application
COPY src ./src
RUN mvn clean package -DskipTests

# Use a lightweight JDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage (adjust JAR name if needed)
COPY --from=build /app/target/Online-Pharmacy-0.0.1-SNAPSHOT.jar .

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/Online-Pharmacy-0.0.1-SNAPSHOT.jar"]
