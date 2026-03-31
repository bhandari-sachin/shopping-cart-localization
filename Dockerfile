# ----------------------
# Build stage
# ----------------------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom and source files
COPY pom.xml .
COPY src ./src

# Build fat JAR (skip tests to speed up, can remove -DskipTests if desired)
RUN mvn clean package -DskipTests

# ----------------------
# Runtime stage
# ----------------------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/shopping-cart-localization.jar app.jar

# Ensure UTF-8 encoding for localization (Japanese, Finnish, Swedish)
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Run the console app
ENTRYPOINT ["java", "-jar", "app.jar"]