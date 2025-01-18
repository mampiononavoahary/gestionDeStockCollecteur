package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Status;
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
public class Transaction implements Serializable {
    private int id_transaction;
    private ProduitAvecDetail produitAvecDetail;
    private DetailTransaction detailTransaction;
    private Double quantite;
    private Unite unite;
    private Double prix_unitaire;
    private Status status;
    private LieuDeTransaction lieu_stock;
}
