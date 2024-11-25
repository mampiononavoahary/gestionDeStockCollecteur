package com.spring.gestiondestock.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://gestionofstock.vercel.app", allowCredentials = "true")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response) {

        AuthenticationResponse authResponse = authenticationService.authenticate(request);

        // Ajout du cookie contenant le JWT
        addJwtToCookie(authResponse.getToken(), response);

        return ResponseEntity.ok(authResponse);
    }

    // Méthode pour ajouter le cookie avec SameSite
    private void addJwtToCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);  // Empêche l'accès au cookie via JavaScript
        cookie.setSecure(true);    // Nécessaire si vous utilisez HTTPS
        cookie.setPath("/");       // Définit le chemin du cookie
        cookie.setMaxAge(36000);    // Durée de vie du cookie (en secondes)

        // Définir SameSite pour les cookies cross-origin
        cookie.setAttribute("SameSite", "None");

        // Ajouter le cookie à la réponse
        response.addCookie(cookie);
    }
}

