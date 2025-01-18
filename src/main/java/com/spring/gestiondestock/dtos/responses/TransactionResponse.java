package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionResponse {
    private int id_transaction;
    private ProduitAvecDetail produitAvecDetail;
    private DetailTransaction detailTransaction;
    private Double quantite;
    private Unite unite;
    private Double prix_unitaire;
    private Status status;
}
