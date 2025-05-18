package com.spring.gestiondestock.dtos.requests;

import lombok.*;

import java.nio.file.LinkOption;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProduitsCollecterRequest {
    private Long id_debit_collecter;
    private int id_produit_avec_detail;
    private Double quantite;
    private String unite;
    private Double prix_unitaire;
    private String lieu_stock;
}
