package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitAvecDetailResponse {
    private int id;
    private Produit produit;
    private DetailProduit detailProduit;
}
