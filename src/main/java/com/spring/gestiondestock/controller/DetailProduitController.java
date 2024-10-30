package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.DetailProduitRequest;
import com.spring.gestiondestock.dtos.responses.DetailProduitResponse;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.service.impl.DetailProduitServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class DetailProduitController {
    private static DetailProduitServiceImpl detailProduitService;
    public DetailProduitController(DetailProduitServiceImpl detailProduitService) {
        DetailProduitController.detailProduitService = detailProduitService;
    }
    @GetMapping("/detailproduits")
    public List<DetailProduitResponse> getDetailProduits() throws SQLException, ClassNotFoundException {
        return detailProduitService.getAllDetailProduit();
    }
    @PostMapping("/detailproduit/post")
    public DetailProduitResponse addDetailProduit(@RequestBody DetailProduitRequest detailProduitRequest) throws SQLException, ClassNotFoundException {
        return detailProduitService.createDetailProduit(detailProduitRequest);
    }
    @PutMapping("/detailproduit/put")
    public DetailProduitResponse updateDetailProduit(@RequestBody DetailProduit detailProduit) throws SQLException, ClassNotFoundException {
        return detailProduitService.updateDetailProduit(detailProduit, detailProduit.getId_detail_produit());
    }
    @DeleteMapping("/detailproduit/delete/{id_detail_produit}")
    public DetailProduitResponse deleteDetailProduit(@PathVariable String id_detail_produit) throws SQLException, ClassNotFoundException {
        return detailProduitService.deleteDetailProduit(Integer.parseInt(id_detail_produit));
    }
    @GetMapping("/detailproduit/{id_detail_produit}")
    public DetailProduitResponse findDetailProduitById(@PathVariable String id_detail_produit) throws SQLException, ClassNotFoundException {
        return detailProduitService.findDetailProduitById(Integer.parseInt(id_detail_produit));
    }
}
