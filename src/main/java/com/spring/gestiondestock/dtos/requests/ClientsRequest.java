package com.spring.gestiondestock.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientsRequest {
    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String nom;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @Size(max = 200, message = "Le prénom ne peut pas dépasser 200 caractères")
    private String prenom;

    @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
    private String adresse;

    @Pattern(regexp = "\\d{10}", message = "Le numéro de téléphone doit comporter 10 chiffres")
    private String telephone;
}
