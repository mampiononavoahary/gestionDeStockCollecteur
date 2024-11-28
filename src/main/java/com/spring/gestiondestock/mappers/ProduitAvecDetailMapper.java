package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.ProduitAvecDetailRequest;
import com.spring.gestiondestock.dtos.responses.ProduitAvecDetailResponse;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProduitAvecDetailMapper {
    public ProduitAvecDetail toEntity(ProduitAvecDetailRequest produitAvecDetailRequest) {
        return ProduitAvecDetail.builder()
                .id_produit(Produit.builder().id_produit(produitAvecDetailRequest.getId_produit()).build())
                .id_detail_produit(DetailProduit.builder().id_detail_produit(produitAvecDetailRequest.getId_detail_produit()).build())
                .build();
    }

    public ProduitAvecDetailResponse toResponse(ProduitAvecDetail produitAvecDetail) {
        return ProduitAvecDetailResponse.builder()
                .produit(Produit.builder().id_produit(produitAvecDetail.getId_produit().getId_produit()).build())
                .detailProduit(DetailProduit.builder().id_detail_produit(produitAvecDetail.getId_detail_produit().getId_detail_produit()).build())
                .build();
    }
}
