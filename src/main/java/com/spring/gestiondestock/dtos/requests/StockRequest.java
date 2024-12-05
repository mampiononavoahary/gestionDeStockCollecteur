package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockRequest {
    private String lieu_de_transaction;
    private int id_produit_avec_detail;
    private Double quantite_stock;
    private String unite;
}
