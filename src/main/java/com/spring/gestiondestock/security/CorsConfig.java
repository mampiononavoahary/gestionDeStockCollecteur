package com.spring.gestiondestock.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://gestionofstock.vercel.app","http://localhost:3000")); // Autoriser l'origine spécifique
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type","Origin","Accept"));
        config.setAllowCredentials(true);  // Autoriser les cookies / credentials

        // Configurer les origines autorisées pour les cookies et les en-têtes
        config.addAllowedOrigin("http://localhost:3000");  // Origine spécifique
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Appliquer la configuration CORS seulement sur /api/**
        return source;
    }
}
