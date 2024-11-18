package com.spring.gestiondestock.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Nom de la vue pour la page de connexion
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Nom de la vue pour la page sécurisée
    }
}
