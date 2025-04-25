package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.ProduitAvecDetailRequest;
import com.spring.gestiondestock.dtos.responses.ProduitAvecDetailResponse;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.TypeProduit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProduitAvecDetailMapper {
    public ProduitAvecDetail toEntity(ProduitAvecDetailRequest produitAvecDetailRequest) {
        ProduitAvecDetail.ProduitAvecDetailBuilder produitAvecDetailBuilder = ProduitAvecDetail.builder()
                .id_produit(Produit.builder().id_produit(produitAvecDetailRequest.getId_produit()).build())
                .id_detail_produit(DetailProduit.builder().id_detail_produit(produitAvecDetailRequest.getId_detail_produit()).build());

        // Vérification de null pour id_type_produit
        if (produitAvecDetailRequest.getId_type_produit() != null) {
            produitAvecDetailBuilder.id_type_produit(TypeProduit.builder().id_type_produit(produitAvecDetailRequest.getId_type_produit()).build());
        } else {
            produitAvecDetailBuilder.id_type_produit(null); // Assigner null si id_type_produit est null
        }

        return produitAvecDetailBuilder.build();
    }


    public ProduitAvecDetailResponse toResponse(ProduitAvecDetail produitAvecDetail) {
        ProduitAvecDetailResponse.ProduitAvecDetailResponseBuilder responseBuilder = ProduitAvecDetailResponse.builder()
                .produit(Produit.builder().id_produit(produitAvecDetail.getId_produit().getId_produit()).build())
                .detailProduit(DetailProduit.builder().id_detail_produit(produitAvecDetail.getId_detail_produit().getId_detail_produit()).build());

        // Vérification de null pour id_type_produit
        if (produitAvecDetail.getId_type_produit() != null) {
            responseBuilder.typeProduit(TypeProduit.builder()
                    .id_type_produit(produitAvecDetail.getId_type_produit().getId_type_produit())
                    .build());
        } else {
            responseBuilder.typeProduit(null); // Assigner null si id_type_produit est null
        }

        return responseBuilder.build();
    }

}
