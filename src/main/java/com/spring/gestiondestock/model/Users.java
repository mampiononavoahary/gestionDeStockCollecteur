package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.RoleUser;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@ToString
public class Users implements Serializable {
    private int id_user;
    private String nom;
    private String prenom;
    private String address;
    private String contact;
    private String image;
    private RoleUser role;
    private String nom_d_utilisateur;
    private String mot_de_passe;
}
