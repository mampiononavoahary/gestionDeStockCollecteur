package com.spring.gestiondestock.model;


import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Accessors(chain = true)
public class TypeProduit implements Serializable {
    private int id_type_produit;
    private String nom_type_produit;
    private String symbole;
    List<ProduitAvecDetail> produitAvecDetailList;
}
