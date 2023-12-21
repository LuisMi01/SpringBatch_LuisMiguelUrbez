# Fase de construcción de Spring Boot
FROM maven:3.8.5-openjdk-17 AS spring-build
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src

# Fase de producción
FROM openjdk:17.0.1-jdk-slim
COPY --from=spring-build /application/target/app-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]