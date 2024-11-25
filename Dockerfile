# Étape 1 : Construire l'application
# Étape 1 : Construction de l'application
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

# Copier uniquement les fichiers nécessaires pour installer les dépendances Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le reste du projet
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Créer une image finale pour exécuter l'application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copier le fichier .env si vous l'utilisez
COPY .env ./
# Copier le fichier JAR généré
COPY --from=build /app/target/gestionDeStock-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","gestionDeStock-0.0.1-SNAPSHOT.jar"]
