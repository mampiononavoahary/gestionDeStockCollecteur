package com.spring.gestiondestock.model.extractModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExtractBilanProduitsCollecter {
    private String nom_produit;
    private Double total_quantite;
    private String Unite;
}
