package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class ProduitsCollecter {
    private Long idProduitCollecter;
    private DebitCollecteur debitCollecteur;
    private ProduitAvecDetail produitAvecDetail;
    private Double quantite;
    private Unite unite;
    private Double prix_unitaire;
    private LieuDeTransaction lieu_de_transaction;
}
