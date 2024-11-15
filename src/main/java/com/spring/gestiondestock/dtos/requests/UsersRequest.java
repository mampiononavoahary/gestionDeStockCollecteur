package com.spring.gestiondestock.dtos.requests;

import com.spring.gestiondestock.model.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class UsersRequest {
    private String nom;
    private String prenom;
    private String address;
    private String contact;
    private String image;
    private RoleUser role;
    private String nom_d_utilisateur;
    private String mot_de_passe;
}
