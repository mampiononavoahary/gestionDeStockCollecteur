package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequest {
    private int id_produit_avec_detail;
    private int id_detail_transaction;
    private double quantite;
    private String unite;
    private String status;
}
