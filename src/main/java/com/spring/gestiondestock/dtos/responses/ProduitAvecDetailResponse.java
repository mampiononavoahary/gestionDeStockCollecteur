package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitAvecDetailResponse {
    private int id;
    private Produit produit;
    private DetailProduit detailProduit;
    List<Transaction> transactionList;
    List<Stock> stockList; ;
}
