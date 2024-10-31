package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitResponse;
import com.spring.gestiondestock.mappers.ClientsMapper;
import com.spring.gestiondestock.mappers.ProduitMapper;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.repositories.impl.ProduitRepositoriesImpl;
import com.spring.gestiondestock.service.ServiceProduit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitServiceImpl implements ServiceProduit {
    private static final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);
    private final ProduitRepositoriesImpl produitRepositories;
    private final ProduitMapper produitMapper;
    private final ClientsMapper clientsMapper;

    public ProduitServiceImpl(ProduitRepositoriesImpl produitRepositories, ProduitMapper produitMapper, ClientsMapper clientsMapper) {
        this.produitRepositories = produitRepositories;
        this.produitMapper = produitMapper;
        this.clientsMapper = clientsMapper;
    }

    @Override
    public List<ProduitResponse> findAll() throws SQLException, ClassNotFoundException {
        List<ProduitResponse> produitResponses = new ArrayList<>();
        var produits = produitRepositories.getProduits();
        for(Produit produit : produits) {
            produitResponses.add(produitMapper.toProduitResponse(produit));
        }
        return produitResponses;
    }

    @Override
    public ProduitResponse findById(int id) throws SQLException, ClassNotFoundException {
        var produit = produitRepositories.getProduit(id);
        if(produit == null) {
            log.info("produit:{} not found", id);
        }
        return produitMapper.toProduitResponse(produit);
    }

    @Override
    public ProduitResponse save(ProduitRequest produitRequest) throws SQLException, ClassNotFoundException {
        var addProduit = produitMapper.toProduit(produitRequest);
        var save = produitRepositories.createProduit(addProduit);
        return produitMapper.toProduitResponse(save);
    }

    @Override
    public List<ProduitResponse> createProduits(List<ProduitRequest> produitRequests) throws SQLException, ClassNotFoundException {
        var allProduits = new ArrayList<ProduitResponse>();
        for(ProduitRequest produitRequest : produitRequests) {
            var addProduit = produitMapper.toProduit(produitRequest);
            var save = produitRepositories.createProduit(addProduit);
            if(save == null) {
                throw new IllegalStateException("Failed to save client: " + produitRequest.getNom_produit());
            }
            var res = produitMapper.toProduitResponse(save);
            allProduits.add(res);
        }
        return allProduits;
    }

    @Override
    public ProduitResponse update(Produit produit, int id) throws SQLException, ClassNotFoundException {
        var produits = produitRepositories.getProduit(id);
        if(produits == null) {
            log.info("produit:{} not found", id);
        }
        var res = produitRepositories.updateProduit(produit, id);
        return produitMapper.toProduitResponse(res);
    }

    @Override
    public ProduitResponse delete(int id) throws SQLException, ClassNotFoundException {
        var produit = produitRepositories.getProduit(id);
        if(produit == null) {
            log.info("produit:{} not found", id);
        }
        var delete = produitRepositories.deleteProduit(id);
        return produitMapper.toProduitResponse(delete);
    }

    @Override
    public ProduitResponse findByName(String nom) throws SQLException, ClassNotFoundException {
        var produit = produitRepositories.getProduitByName(nom);
        if(produit == null) {
            log.info("produit:{} not found", nom);
        }
        return produitMapper.toProduitResponse(produit);
    }
}
