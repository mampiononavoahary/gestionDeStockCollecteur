package com.spring.gestiondestock.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString
public class Clients implements Serializable {
    private int id_clients;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
}
