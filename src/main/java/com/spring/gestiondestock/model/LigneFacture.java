package com.spring.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LigneFacture {
    private String produit;
    private Double quantite;
    private String unite;
    private Double prix;
    private Double total;
}
