package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.Transaction;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProduitAvecDetailResponse {
    private int id;
    private Produit produit;
    private DetailProduit detailProduit;
    List<Transaction> transactionList;
    List<Stock> stockList; ;
}
