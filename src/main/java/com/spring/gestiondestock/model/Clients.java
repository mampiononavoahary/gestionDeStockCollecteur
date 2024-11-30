package com.spring.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString
public class Clients implements Serializable {

    // Annotation pour mapper "id_client" dans le JSON vers "id_clients"
    @JsonProperty("id_client")
    private int id_clients;

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;

    // Ajout d'une annotation @JsonIgnore pour éviter les erreurs lors de la désérialisation
    @JsonIgnore
    private List<DetailTransaction> detailTransactions;

    // Constructeur supplémentaire pour désérialiser uniquement l'id
    @JsonCreator
    public Clients(@JsonProperty("id_client") int id_clients) {
        this.id_clients = id_clients;
    }
}

