package com.spring.gestiondestock.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("registerRequest") String RegisterRequestJson) throws IOException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest registerRequest = objectMapper.readValue(RegisterRequestJson, RegisterRequest.class);
        if (file.isEmpty() || registerRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        return ResponseEntity.ok(authenticationService.register(file,registerRequest));
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

