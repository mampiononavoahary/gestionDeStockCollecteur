FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/gestionDeStock-0.0.1-SNAPSHOT.jar gestionDeStock.jar
EXPOSE 3306
ENTRYPOINT ["java","-jar","gestionDeStock.jar"]