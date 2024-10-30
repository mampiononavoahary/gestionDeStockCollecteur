package com.spring.gestiondestock.model;

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
    private int id_produit;
    private Double quantite;
}
