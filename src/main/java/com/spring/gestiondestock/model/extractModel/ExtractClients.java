 package com.spring.gestiondestock.model.extractModel;

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
public class ExtractClients implements Serializable {
    private int id_client;
    private String nom;
    private String prenom;
    private int total_transaction;
    private Double total_paye;
    private Double total_en_attente;
}

