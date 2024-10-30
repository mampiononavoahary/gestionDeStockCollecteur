package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientsRequest {
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
}
