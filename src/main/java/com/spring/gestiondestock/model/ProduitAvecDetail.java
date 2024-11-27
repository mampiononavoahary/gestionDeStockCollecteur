package com.spring.gestiondestock.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ProduitAvecDetail {
    private int id_produit_avec_detail;
    private Produit id_produit;
    private DetailProduit id_detail_produit;
    private List<Transaction> transactionList;
    private List<Stock> stockList;
}
