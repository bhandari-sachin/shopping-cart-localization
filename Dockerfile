# Stage 1: Build JAR using Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy POM and source code
COPY pom.xml .
COPY src ./src

# Build the fat JAR (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/target/shopping-cart-localization.jar app.jar

# Run the console application
CMD ["java", "-jar", "app.jar"]