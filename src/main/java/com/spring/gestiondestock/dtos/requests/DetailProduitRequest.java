package com.spring.gestiondestock.dtos.requests;

import com.spring.gestiondestock.model.enums.CategoryProduit;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailProduitRequest {
    private String nom_detail;
    private String symbole;
    private CategoryProduit categorie_produit;
    private String description;
    private Double prix_d_achat;
    private Double prix_de_vente;
    private Unite unite;
}
