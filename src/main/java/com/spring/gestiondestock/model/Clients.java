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

    @JsonProperty("id_client")
    private int id_clients;

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;

    @JsonIgnore
    private List<DetailTransaction> detailTransactions;


    @JsonCreator
    public Clients(@JsonProperty("id_client") int id_clients) {
        this.id_clients = id_clients;
    }
}

