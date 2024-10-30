package com.spring.gestiondestock.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString
public class DetailProduit implements Serializable {
    private int id_detail_produit;
    private String nom_detail;
    private String description;
    private Double prix_d_achat;
    private Double prix_de_vente;
}
