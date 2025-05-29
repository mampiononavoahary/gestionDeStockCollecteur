package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class DebitsExtract {
    private int id_debit_collecteur;
    private LocalDateTime date_de_debit;
    private String lieu_de_collection;
    private Double depense;
    private String description;
    List<ProduitsCollecterExtract> produits_collecter_extract;
}
