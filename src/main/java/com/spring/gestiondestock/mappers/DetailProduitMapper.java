package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DetailProduitMapper {
    public DetailProduit toDetailProduit(DetailProduitRequest detailProduitRequest) {
        return DetailProduit.builder()
                .nom_detail(detailProduitRequest.getNom_detail())
                .symbole(detailProduitRequest.getSymbole())
                .description(detailProduitRequest.getDescription())
                .prix_d_achat(detailProduitRequest.getPrix_d_achat())
                .prix_de_vente(detailProduitRequest.getPrix_de_vente())
                .unite(detailProduitRequest.getUnite())
                .build();
    }
    public DetailProduitResponse toDetailProduitResponse(DetailProduit detailProduit) {
        return new DetailProduitResponse(
                detailProduit.getId_detail_produit(),
                detailProduit.getNom_detail(),
                detailProduit.getSymbole(),
                detailProduit.getDescription(),
                detailProduit.getPrix_d_achat(),
                detailProduit.getPrix_de_vente(),
                detailProduit.getUnite()
        );
    }
}
