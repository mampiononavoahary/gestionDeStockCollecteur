package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeProduitRequest {
    private String nom_type_produit;
    private String symbole;
}
