# --- Stage 1: Build the JAR ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy the Parent POM and the Protos (Shared files)
COPY pom.xml .
COPY protos ./protos

# 2. Copy the specific service code
COPY service-A ./service-A
COPY service-B ./service-B
COPY service-D ./service-D

# 3. Build only Service A (and its dependencies)
# We use -pl (project list) to tell Maven exactly what to build
RUN mvn clean package -pl service-A -am -DskipTests

# --- Stage 2: Create the Runtime Image ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the compiled JAR from the build stage
COPY --from=build /app/service-A/target/*.jar serviceA.jar

# Expose Service A's port (usually 8080 for REST)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "serviceA.jar"]