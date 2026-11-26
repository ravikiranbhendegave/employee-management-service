# Stage 1: Build Spring Boot application
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package

# Stage 2: Run Spring Boot application
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["sh", "-c", "exec java -jar app.jar"]