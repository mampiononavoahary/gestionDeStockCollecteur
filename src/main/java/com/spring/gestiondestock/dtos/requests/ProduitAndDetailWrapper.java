package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProduitAndDetailWrapper {
    private int produit;
    private Integer type_produit;
    private DetailProduitRequest detailProduitRequest;
}
