# Stage 1: Build Spring Boot application
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

#RUN mvn -B clean package

RUN for attempt in 1 2 3 4 5; do \
      mvn -B -ntp clean package && exit 0; \
      echo "Maven build failed. Retrying in $((attempt * 20)) seconds..."; \
      sleep $((attempt * 20)); \
    done; \
    exit 1

# Stage 2: Run Spring Boot application
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["sh", "-c", "exec java -jar app.jar"]