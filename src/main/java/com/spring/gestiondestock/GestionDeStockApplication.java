package com.spring.gestiondestock;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionDeStockApplication {

    public static void main(String[] args) {
        // Charger le fichier .env au démarrage de l'application
        Dotenv dotenv = Dotenv.load();
        // Utilisez les variables d'environnement chargées
        String dbUrl = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        System.setProperty("DB_URL", dbUrl);
        System.setProperty("DB_USERNAME", dbUsername);
        System.setProperty("DB_PASSWORD", dbPassword);

        SpringApplication.run(GestionDeStockApplication.class, args);
    }
    
}