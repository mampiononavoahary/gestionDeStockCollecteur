package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.*;
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
    private TypeProduit typeProduit;
    private DetailProduit detailProduit;
    List<Transaction> transactionList;
    List<Stock> stockList; ;
}
