package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.Unite;
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
    private String symbole;
    private String description;
    private Double prix_d_achat;
    private Double prix_de_vente;
    private Unite unite;
}
