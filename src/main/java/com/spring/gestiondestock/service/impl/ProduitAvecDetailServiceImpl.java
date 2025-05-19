package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.requests.ProduitAvecDetailRequest;
import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitAvecDetailResponse;
import com.spring.gestiondestock.mappers.DetailProduitMapper;
import com.spring.gestiondestock.mappers.ProduitAvecDetailMapper;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.repositories.impl.DetailProduitRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitRepositoriesImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Service
public class ProduitAvecDetailServiceImpl {
    private static ProduitRepositoriesImpl produitRepositories;
    private static DetailProduitRepositoriesImpl detailProduitRepositories;
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    private static DetailProduitMapper detailProduitMapper;
    private static ProduitAvecDetailMapper produitAvecDetailMapper;

    public ProduitAvecDetailServiceImpl(DetailProduitMapper detailProduitMapper, ProduitRepositoriesImpl produitRepositories, DetailProduitRepositoriesImpl detailProduitRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories, ProduitAvecDetailMapper produitAvecDetailMapper) {
        ProduitAvecDetailServiceImpl.produitRepositories = produitRepositories;
        ProduitAvecDetailServiceImpl.detailProduitRepositories = detailProduitRepositories;
        ProduitAvecDetailServiceImpl.produitAvecDetailRepositories = produitAvecDetailRepositories;
        ProduitAvecDetailServiceImpl.produitAvecDetailMapper = produitAvecDetailMapper;
        ProduitAvecDetailServiceImpl.detailProduitMapper = detailProduitMapper;
    }


    public ProduitAvecDetailResponse createProduitAvecDetail(int produit, Integer type_produit, DetailProduitRequest detailProduitRequest) throws SQLException, ClassNotFoundException {
        var detailProduit = detailProduitMapper.toDetailProduit( detailProduitRequest);
        var saveDetailProduitRequest = detailProduitRepositories.toSave(detailProduit);

        ProduitAvecDetailRequest produitAvecDetailRequest = new ProduitAvecDetailRequest();
        produitAvecDetailRequest.setId_produit(produit);

        // Gestion de type_produit null
        if (type_produit != null) {
            produitAvecDetailRequest.setId_type_produit(type_produit);
        } else {
            produitAvecDetailRequest.setId_type_produit(null);  // Assigner null si type_produit est null
        }

        produitAvecDetailRequest.setId_detail_produit(saveDetailProduitRequest.getId_detail_produit());

        var produitAvecDetail = produitAvecDetailRepositories.saveProduitAvecDetail(produitAvecDetailMapper.toEntity(produitAvecDetailRequest));
        return produitAvecDetailMapper.toResponse(produitAvecDetail);
    }

}
