# Usar imagem oficial do Java 17
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expor a porta que a aplicação vai usar
EXPOSE 8080

# Rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
