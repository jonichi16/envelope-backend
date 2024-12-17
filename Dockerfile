FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -ntp -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/service/core/target/core-0.0.1-exec.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]