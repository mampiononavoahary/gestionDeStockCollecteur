package com.spring.gestiondestock.model.extractModel;

import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockWithDetail implements Serializable {
    private int id_stock;
    private String lieu_stock;
    private int id_produit_avec_detail;
    private Double quantite_stock;
    private Unite unite;
    private String nom_detail;
    private String symbole;
    private String image_url;
}
