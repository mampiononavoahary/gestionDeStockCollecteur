package com.spring.gestiondestock.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@ToString
public class Produit implements Serializable {
    private int id_produit;
    private String nom_produit;
    private List<ProduitAvecDetail> listProduitAvecDetail;
}
