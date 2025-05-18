package com.spring.gestiondestock.model.extractModel;

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
public class ProduitsCollecterExtract {
    private int id_produit_collecter;
    private int id_produit_avec_detail;
    private String nom_detail;
    private Double quantite;
    private String unite;
    private Double prix_unitaire;
    private String lieu_stock;
    private Double total;
}
