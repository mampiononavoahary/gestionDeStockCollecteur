package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.requests.ProduitAvecDetailRequest;
import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitAvecDetailResponse;
import com.spring.gestiondestock.mappers.ProduitAvecDetailMapper;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.repositories.impl.DetailProduitRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProduitAvecDetailServiceImpl {
    private static ProduitRepositoriesImpl produitRepositories;
    private static DetailProduitRepositoriesImpl detailProduitRepositories;
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    private static ProduitAvecDetailMapper produitAvecDetailMapper;

    public ProduitAvecDetailServiceImpl(ProduitRepositoriesImpl produitRepositories, DetailProduitRepositoriesImpl detailProduitRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories, ProduitAvecDetailMapper produitAvecDetailMapper) {
        ProduitAvecDetailServiceImpl.produitRepositories = produitRepositories;
        ProduitAvecDetailServiceImpl.detailProduitRepositories = detailProduitRepositories;
        ProduitAvecDetailServiceImpl.produitAvecDetailRepositories = produitAvecDetailRepositories;
        ProduitAvecDetailServiceImpl.produitAvecDetailMapper = produitAvecDetailMapper;
    }

    public ProduitAvecDetailResponse createProduitAvecDetail(Produit produit, DetailProduit detailProduitRequest) throws SQLException, ClassNotFoundException {
        var saveProduitRequest = produitRepositories.createProduit(produit);
        var saveDetailProduitRequest = detailProduitRepositories.toSave(detailProduitRequest);

        ProduitAvecDetailRequest produitAvecDetailRequest = new ProduitAvecDetailRequest();
        produitAvecDetailRequest.setId_produit(saveProduitRequest.getId_produit());
        produitAvecDetailRequest.setId_detail_produit(saveDetailProduitRequest.getId_detail_produit());
        var produitAvecDetail = produitAvecDetailRepositories.saveProduitAvecDetail(produitAvecDetailMapper.toEntity(produitAvecDetailRequest));
        return produitAvecDetailMapper.toResponse(produitAvecDetail);
    }
}
