package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.lieu_transaction;
import com.spring.gestiondestock.model.enums.unite;
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
    private lieu_transaction lieu_transaction;
    private Produit id_produit;
    private Double quantite_stock;
    private unite unite;
}
