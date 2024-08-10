# Build stage
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml ./
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Final stage
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/smsv2-0.0.1-SNAPSHOT.jar smsv2.jar

# Expose the port on which the application will run
EXPOSE 9091

# Run the JAR file
ENTRYPOINT ["java", "-jar", "smsv2.jar"]
