package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExtractBilanCreditsCollecteur {
    private String collecteur;
    private Double total_credit;
    private List<ExtractBilanProduitsCollecter> produits;
}
