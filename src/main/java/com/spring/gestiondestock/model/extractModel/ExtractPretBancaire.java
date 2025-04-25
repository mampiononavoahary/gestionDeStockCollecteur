package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExtractPretBancaire {
    private int id_pret_bancaire;
    private LocalDateTime date_de_pret;
    private String produit;
    private Double quantite;
    private String unite;
    private Double prix;
    private Double taux_augmentation;
    private Double taux_mensuel;
    private LocalDateTime date_de_remboursement;
}
