package com.spring.gestiondestock.model;

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
}
