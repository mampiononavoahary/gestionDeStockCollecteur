package com.spring.gestiondestock.dtos.requests;

import com.spring.gestiondestock.model.DetailProduit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitRequest {
    private String nom_produit;
}
