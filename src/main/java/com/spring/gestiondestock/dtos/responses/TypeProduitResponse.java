package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.ProduitAvecDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeProduitResponse {
    private int id_type_produit;
    private String nom_type_produit;
    private String symbole;
    List<ProduitAvecDetail> produitAvecDetailList;
}
