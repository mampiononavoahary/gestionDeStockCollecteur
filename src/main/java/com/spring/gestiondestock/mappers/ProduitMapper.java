package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProduitMapper {

    public Produit toProduit(ProduitRequest produitRequest) {
        return Produit.builder()
                .nom_produit(produitRequest.getNom_produit())
                .build();
    }
    public ProduitResponse toProduitResponse(Produit produit) {
        return new ProduitResponse(
                produit.getId_produit(),
                produit.getNom_produit(),
                produit.getListProduitAvecDetail()
        );
    }
}
