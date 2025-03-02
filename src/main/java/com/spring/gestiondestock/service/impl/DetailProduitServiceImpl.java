package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.mappers.DetailProduitMapper;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.repositories.impl.DetailProduitRepositoriesImpl;
import com.spring.gestiondestock.service.ServiceDetailProduit;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetailProduitServiceImpl implements ServiceDetailProduit {
    private static DetailProduitRepositoriesImpl detailProduitRepositories;
    private static DetailProduitMapper detailProduitMapper;

    public DetailProduitServiceImpl(DetailProduitMapper detailProduitMapper, DetailProduitRepositoriesImpl detailProduitRepositories) {
        DetailProduitServiceImpl.detailProduitMapper = detailProduitMapper;
        DetailProduitServiceImpl.detailProduitRepositories = detailProduitRepositories;
    }

    @Override
    public DetailProduitResponse createDetailProduit(MultipartFile image_url,DetailProduitRequest detailProduitRequest) throws SQLException, ClassNotFoundException {
        var detail = detailProduitMapper.toDetailProduit(image_url,detailProduitRequest);
        var save = detailProduitRepositories.toSave(detail);
        return detailProduitMapper.toDetailProduitResponse(save);
    }

    @Override
    public DetailProduitResponse updateDetailProduit(DetailProduit detailProduit, int id_detail_produit) throws SQLException, ClassNotFoundException {
        var detail = detailProduitRepositories.getById(id_detail_produit);
        if (detail == null){
            System.out.println("detail produit not found");
            throw new SQLException("detail produit with id "+id_detail_produit+" not found");
        }
        detailProduitRepositories.toUpdate(detailProduit);
        return detailProduitMapper.toDetailProduitResponse(detailProduit);
    }

    @Override
    public DetailProduitResponse deleteDetailProduit(int id_detail_produit) throws SQLException, ClassNotFoundException {
        var detail = detailProduitRepositories.getById(id_detail_produit);
        if (detail == null){
            System.out.println("detail produit not found");
            throw new SQLException("detail produit with id "+id_detail_produit+" not found");
        }
        detailProduitRepositories.toDelete(id_detail_produit);
        return null;
    }

    @Override
    public List<DetailProduitResponse> getAllDetailProduit() throws SQLException, ClassNotFoundException {
        var getAll = detailProduitRepositories.getDetailProduits();
        List<DetailProduitResponse> detailProduitResponses = new ArrayList<>();
        for(DetailProduit detailProduit: getAll){
            detailProduitResponses.add(detailProduitMapper.toDetailProduitResponse(detailProduit));
        }
        return detailProduitResponses;
    }

    @Override
    public DetailProduitResponse findDetailProduitById(int id_detail_produit) throws SQLException, ClassNotFoundException {
        var detail = detailProduitRepositories.getById(id_detail_produit);
        if (detail == null){
            System.out.println("detail produit not found");
        }
        return detailProduitMapper.toDetailProduitResponse(detail);
    }
}
