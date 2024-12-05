package com.spring.gestiondestock.dtos.responses;

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
public class StockResponse {
    private int id_stock;
    private LieuDeTransaction lieu_de_transaction;
    private ProduitAvecDetail id_produit_avec_detail;
    private Double quantite_stock;
    private Unite unite;
}
