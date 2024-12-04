package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExtractLatestTransaction {
    private int id_transaction;
    private String nom;
    private Timestamp date_de_transaction;
    private String nom_detail;
    private Double quantite;
    private String unite;
}
