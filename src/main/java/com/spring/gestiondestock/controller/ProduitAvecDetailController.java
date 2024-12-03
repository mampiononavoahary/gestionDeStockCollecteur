package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.extractModel.ExtractProduitWitDetail;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/produitavecdetails")
public class ProduitAvecDetailController {
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    public ProduitAvecDetailController(ProduitAvecDetailRepositoriesImpl repositories) {
        produitAvecDetailRepositories = repositories;
    }
    @GetMapping
    public List<ProduitAvecDetail> getAllProduitAvecDetail() throws SQLException, ClassNotFoundException {
        return produitAvecDetailRepositories.getProduitAvecDetail();
    }
    @GetMapping("/{id_produit_avec_detail}")
    public ProduitAvecDetail getById(@PathVariable String id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        return produitAvecDetailRepositories.findProduitAvecDetailById(Integer.parseInt(id_produit_avec_detail));
    }
    @PostMapping
    public ProduitAvecDetail save(@RequestBody ProduitAvecDetail produitAvecDetail) throws SQLException, ClassNotFoundException {
        return produitAvecDetailRepositories.saveProduitAvecDetail(produitAvecDetail);
    }
    @DeleteMapping("/delete/{id_produit_avec_detail}")
    public void delete(@PathVariable String id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        produitAvecDetailRepositories.deleteProduitAvecDetailById(Integer.parseInt(id_produit_avec_detail));
    }
    @PutMapping("/put/{id_produit_avec_detail}")
    public ProduitAvecDetail updateProduitAvecDetail(@RequestBody ProduitAvecDetail produitAvecDetail, @PathVariable String id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        return produitAvecDetailRepositories.updateProduitAvecDetail(produitAvecDetail, Integer.parseInt(id_produit_avec_detail));
    }
    @GetMapping("/idandname")
    public List<ExtractProduitWitDetail> getIdAndName() throws SQLException, ClassNotFoundException {
        return produitAvecDetailRepositories.getIdAndNameDetail();
    }
}
