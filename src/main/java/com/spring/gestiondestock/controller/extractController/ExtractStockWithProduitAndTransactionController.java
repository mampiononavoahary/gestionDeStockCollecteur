package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.StockWithProduitAndTransaction;
import com.spring.gestiondestock.service.extractService.ExtractStockWithProduitAndTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class ExtractStockWithProduitAndTransactionController {
    private static ExtractStockWithProduitAndTransactionService extractStockWithProduitAndTransactionService;

    public ExtractStockWithProduitAndTransactionController(ExtractStockWithProduitAndTransactionService extractStockWithProduitAndTransactionService) {
        ExtractStockWithProduitAndTransactionController.extractStockWithProduitAndTransactionService = extractStockWithProduitAndTransactionService;
    }
    @GetMapping("/{lieu}/{nom_produit}")
    public List<StockWithProduitAndTransaction> findByLieuAndProduit(@PathVariable String lieu, @PathVariable String nom_produit) throws SQLException, ClassNotFoundException {
        return extractStockWithProduitAndTransactionService.findByLieuAndProduit(lieu, nom_produit);
    }
}
