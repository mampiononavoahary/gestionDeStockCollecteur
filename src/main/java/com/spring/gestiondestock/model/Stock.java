package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class Stock implements Serializable {
    private int id_stock;
    private LieuDeTransaction lieu_de_transaction;
    private ProduitAvecDetail produitAvecDetail;
    private Double quantite_stock;
    private Unite unite;
}
