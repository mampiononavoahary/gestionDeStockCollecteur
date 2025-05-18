package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProduitsCollecterResponse {
    private Long id_produit_collecter;
    private DebitCollecteur debitCollecteur;
    private ProduitAvecDetail produitAvecDetail;
    private Double quantite;
    private Unite unite;
    private Double prix_unitaire;
    private LieuDeTransaction lieu_stock;
}
