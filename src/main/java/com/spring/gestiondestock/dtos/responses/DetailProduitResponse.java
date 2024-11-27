package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DetailProduitResponse {
    private int id_detail_produit;
    private String nom_detail;
    private String symbole;
    private String description;
    private Double prix_d_achat;
    private Double prix_de_vente;
    private Unite unite;
    List<ProduitAvecDetail> produit_avec_detail;
}
