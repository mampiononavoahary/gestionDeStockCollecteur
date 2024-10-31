package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailProduit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProduitResponse {
    private int id_produit;
    private String nom_produit;
    private DetailProduit detailProduit;
}
