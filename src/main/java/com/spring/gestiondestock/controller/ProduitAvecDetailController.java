package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.ProduitAndDetailWrapper;
import com.spring.gestiondestock.dtos.responses.ProduitAvecDetailResponse;
import com.spring.gestiondestock.mappers.DetailProduitMapper;
import com.spring.gestiondestock.mappers.ProduitMapper;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.extractModel.ExtractProduitWitDetail;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.service.impl.ProduitAvecDetailServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/produitavecdetails")
public class ProduitAvecDetailController {
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    private static ProduitAvecDetailServiceImpl produitAvecDetailService;
    private static ProduitMapper produitMapper;
    private static DetailProduitMapper detailProduitMapper;
    public ProduitAvecDetailController(ProduitMapper produitMapper, DetailProduitMapper detailProduitMapper, ProduitAvecDetailRepositoriesImpl repositories, ProduitAvecDetailServiceImpl produitAvecDetailService) {
        ProduitAvecDetailController.produitAvecDetailRepositories = repositories;
        ProduitAvecDetailController.produitAvecDetailService = produitAvecDetailService;
        ProduitAvecDetailController.produitMapper = produitMapper;
        ProduitAvecDetailController.detailProduitMapper = detailProduitMapper;
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
    public ProduitAvecDetailResponse save(@RequestBody ProduitAndDetailWrapper produitAvecDetail) throws SQLException, ClassNotFoundException {
        return produitAvecDetailService.createProduitAvecDetail(produitMapper.toProduit(produitAvecDetail.getProduitRequest()), detailProduitMapper.toDetailProduit(produitAvecDetail.getDetailProduitRequest()));
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
