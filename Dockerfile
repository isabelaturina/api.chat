# Estágio 1: Build (Construir a aplicação)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Rodar a aplicação)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080 # ou a porta que sua API usa
ENTRYPOINT ["java", "-jar", "app.jar"]