package com.spring.gestiondestock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private TypeProduit id_type_produit;
    private DetailProduit id_detail_produit;
    private List<Transaction> transactionList;
    private List<Stock> stockList;
}
