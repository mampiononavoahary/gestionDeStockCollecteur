package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class ExtractTransaction {
    private int id_transaction;
    private String nom_client;
    private String nom_produit;
    private Timestamp date_transaction;
    private String lieu_transaction;
    private Double quantite;
    private String unite;
    private String status;
}
