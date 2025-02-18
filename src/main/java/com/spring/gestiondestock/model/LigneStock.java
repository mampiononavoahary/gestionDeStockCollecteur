package com.spring.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LigneStock {
    private Double quantite;
    private String unite;
    private String produit;
}
