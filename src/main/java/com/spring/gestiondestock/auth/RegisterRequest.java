package com.spring.gestiondestock.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String address;
    private String contact;
    private String role;
    private String username;
    private String password;
}
