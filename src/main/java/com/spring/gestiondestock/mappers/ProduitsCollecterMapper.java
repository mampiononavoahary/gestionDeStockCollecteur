package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.ProduitsCollecterRequest;
import com.spring.gestiondestock.dtos.responses.ProduitsCollecterResponse;
import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.ProduitsCollecter;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import org.springframework.stereotype.Component;

@Component
public class ProduitsCollecterMapper {
    public ProduitsCollecter toEntity(ProduitsCollecterRequest produitsCollecterRequest) {
        return ProduitsCollecter.builder()
                .debitCollecteur(DebitCollecteur.builder().idDebitCollecteur(produitsCollecterRequest.getId_debit_collecter()).build())
                .produitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(produitsCollecterRequest.getId_produit_avec_detail()).build().getId_produit_avec_detail())
                .quantite(produitsCollecterRequest.getQuantite())
                .unite(Unite.valueOf(produitsCollecterRequest.getUnite()))
                .prix_unitaire(produitsCollecterRequest.getPrix_unitaire())
                .lieu_stock(LieuDeTransaction.valueOf(produitsCollecterRequest.getLieu_stock()))
                .build();
    }

    public ProduitsCollecterResponse toResponse(ProduitsCollecter produitsCollecter) {
        return ProduitsCollecterResponse.builder()
                .id_produit_collecter(produitsCollecter.getIdProduitCollecter())
                .debitCollecteur(produitsCollecter.getDebitCollecteur())
                .produitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(produitsCollecter.getProduitAvecDetail()).build())
                .quantite(produitsCollecter.getQuantite())
                .unite(produitsCollecter.getUnite())
                .prix_unitaire(produitsCollecter.getPrix_unitaire())
                .lieu_stock(produitsCollecter.getLieu_stock())
                .build();
    }
}
