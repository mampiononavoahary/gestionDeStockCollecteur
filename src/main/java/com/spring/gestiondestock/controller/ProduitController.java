package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.ProduitRequest;
import com.spring.gestiondestock.dtos.responses.ProduitResponse;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.repositories.impl.ProduitRepositoriesImpl;
import com.spring.gestiondestock.service.impl.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProduitController {
    private final ProduitServiceImpl produitService;

    @Autowired
    public ProduitController(ProduitServiceImpl produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/produits")
    public List<ProduitResponse> getAllProduits() throws SQLException, ClassNotFoundException {
        return produitService.findAll();
    }
    @GetMapping("/produit/{id_produit}")
    public ProduitResponse getProduitById(@PathVariable String id_produit) throws SQLException, ClassNotFoundException {
        return produitService.findById(Integer.parseInt(id_produit));
    }
    @PostMapping("/produit/post")
    public ProduitResponse createProduit(@RequestBody ProduitRequest produitRequest) throws SQLException, ClassNotFoundException {
        return produitService.save(produitRequest);
    }
    @PostMapping("/produits/posts")
    public List<ProduitResponse> saveAllProduits(@RequestBody List<ProduitRequest> produitRequests) throws SQLException, ClassNotFoundException {
        return produitService.createProduits(produitRequests);
    }
    @GetMapping("/produit/nom/{nom}")
    public ProduitResponse getProduitByName(@PathVariable String nom) throws SQLException, ClassNotFoundException {
        return produitService.findByName(nom);
    }
    @PutMapping("/produit/put")
    public ProduitResponse updateProduit(@RequestBody Produit produit) throws SQLException, ClassNotFoundException {
        return produitService.update(produit, produit.getId_produit());
    }
    @DeleteMapping("/produit/{id_produit}")
    public ProduitResponse deleteProduit(@PathVariable String id_produit) throws SQLException, ClassNotFoundException {
        return produitService.delete(Integer.parseInt(id_produit));
    }
}
