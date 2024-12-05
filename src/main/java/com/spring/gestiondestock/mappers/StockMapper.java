package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.StockRequest;
import com.spring.gestiondestock.dtos.responses.StockResponse;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockMapper {
    public Stock toEntity(StockRequest stockRequest){
        return Stock.builder()
                .lieu_de_transaction(LieuDeTransaction.valueOf(stockRequest.getLieu_de_transaction()))
                .produitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(stockRequest.getId_produit_avec_detail()).build())
                .quantite_stock(stockRequest.getQuantite_stock())
                .unite(Unite.valueOf(stockRequest.getUnite()))
                .build();
    }
    public StockResponse toResponse(Stock stock){
        return StockResponse.builder()
                .id_stock(stock.getId_stock())
                .lieu_de_transaction(stock.getLieu_de_transaction())
                .id_produit_avec_detail(ProduitAvecDetail.builder().id_produit_avec_detail(stock.getProduitAvecDetail().getId_produit_avec_detail()).build())
                .quantite_stock(stock.getQuantite_stock())
                .unite(stock.getUnite())
                .build();
    }
}
