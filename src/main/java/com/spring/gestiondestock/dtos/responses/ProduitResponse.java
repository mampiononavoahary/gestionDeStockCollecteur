package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProduitResponse {
    private int id_produit;
    private String nom_produit;
    private List<ProduitAvecDetail> produitAvecDetail; ;
}
