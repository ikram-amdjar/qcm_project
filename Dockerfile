# --- ÉTAPE 1 : Build avec Maven ---
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# --- ÉTAPE 2 : Image Java légère pour exécuter l'application ---
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copier le JAR depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]