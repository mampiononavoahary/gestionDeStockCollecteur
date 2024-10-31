package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitAvecDetailRequest {
    private int id_produit;
    private int id_detail_produit;
}
