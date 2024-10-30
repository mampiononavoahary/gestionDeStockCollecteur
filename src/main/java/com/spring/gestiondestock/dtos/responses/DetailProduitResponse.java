package com.spring.gestiondestock.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DetailProduitResponse {
    private int id_detail_produit;
    private String nom_detail;
    private String description;
    private Double prix_d_achat;
    private Double prix_de_vente;
}
