package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.requests.ProduitAvecDetailRequest;
import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;

import java.sql.SQLException;
import java.util.List;

public interface ServiceProduit {
    List<ProduitResponse> findAll() throws SQLException, ClassNotFoundException;
    ProduitResponse findById(int id) throws SQLException, ClassNotFoundException;
    ProduitResponse save(ProduitRequest produitRequest) throws SQLException, ClassNotFoundException;
    List<ProduitResponse> createProduits(List<ProduitRequest> produitRequests) throws SQLException, ClassNotFoundException;
    ProduitResponse update(Produit produit,int id) throws SQLException, ClassNotFoundException;
    ProduitResponse delete(int id) throws SQLException, ClassNotFoundException;
    ProduitResponse findByName(String nom) throws SQLException, ClassNotFoundException;
}
