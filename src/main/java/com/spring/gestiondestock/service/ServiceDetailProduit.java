package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface ServiceDetailProduit {
    DetailProduitResponse createDetailProduit(MultipartFile image_url,DetailProduitRequest detailProduitRequest) throws SQLException, ClassNotFoundException;
    DetailProduitResponse updateDetailProduit(DetailProduit detailProduit, int id_detail_produit) throws SQLException, ClassNotFoundException;
    DetailProduitResponse deleteDetailProduit(int id_detail_produit) throws SQLException, ClassNotFoundException;
    List<DetailProduitResponse> getAllDetailProduit() throws SQLException, ClassNotFoundException;
    DetailProduitResponse findDetailProduitById(int id_detail_produit) throws SQLException, ClassNotFoundException;
}
