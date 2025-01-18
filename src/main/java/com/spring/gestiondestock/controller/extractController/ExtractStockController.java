package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.StockWithDetail;
import com.spring.gestiondestock.service.extractService.ExtractStockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/extract/stock")
public class ExtractStockController {
    private static ExtractStockService extractStockService;

    public ExtractStockController(ExtractStockService extractStockService) {
        ExtractStockController.extractStockService = extractStockService;
    }

    @GetMapping
    public List<StockWithDetail> findAll() throws SQLException, ClassNotFoundException {
        return extractStockService.findAll();
    }
    @GetMapping("/{lieu}/{nom_produit}")
    public StockWithDetail findByLieuAndProduit(@PathVariable String lieu, @PathVariable String nom_produit) throws SQLException, ClassNotFoundException {
        return extractStockService.findByLieuAndProduit(lieu, nom_produit);
    }
}
