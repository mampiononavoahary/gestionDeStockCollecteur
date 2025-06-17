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
public class CreditsExtract {
    private int id_credit_collecteur;
    private String referance_credit;
    private String description;
    private boolean status;
    private LocalDateTime date_de_credit;
    private Double montant_credit;
    private Double recentreste;
    private Double total_depense;
    private Double total_debit;
    private Double reste;
    List<DebitsExtract> debit_extract;
}
