FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/smsv2-0.0.1-SNAPSHOT.jar smsv2.jar
EXPOSE 9091
ENTRYPOINT [ "java","-jar","smsv2.jar" ]