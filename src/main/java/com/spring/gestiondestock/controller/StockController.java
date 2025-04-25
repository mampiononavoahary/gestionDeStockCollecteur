package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.StockRequest;
import com.spring.gestiondestock.dtos.responses.StockResponse;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.service.impl.StockServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final StockServiceImpl stockService;
    public StockController(StockServiceImpl stockService){
        this.stockService = stockService;
    }
    @GetMapping("/{id_produit_avec_detail}")
    public StockResponse findById(@PathVariable String id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        return stockService.findById(Integer.parseInt(id_produit_avec_detail));
    }
    @PostMapping("/create")
    public StockResponse save(@RequestBody StockRequest stockRequest) throws SQLException, ClassNotFoundException {
        return stockService.save(stockRequest);
    }
    @PutMapping("/put/{id_produit_avec_detail}")
    public Stock update(@RequestBody Stock stockRequest, @PathVariable String id_produit_avec_detail) throws SQLException, ClassNotFoundException {
        return stockService.update(stockRequest, Integer.parseInt(id_produit_avec_detail));
    }
    @PutMapping("/put/quantite/{id_stock}")
    public Double updateQuantite(@RequestBody Double quantite, @PathVariable String id_stock) throws SQLException, ClassNotFoundException {
        return stockService.updateQuantiteStock(quantite, Integer.parseInt(id_stock));
    }
    @PostMapping("/transform")
    public String produitPremierToFinie(@RequestParam String lieu_stock,@RequestParam String id_premier,@RequestParam String id_finie,@RequestParam String quantite) throws SQLException, ClassNotFoundException {
        return stockService.produitPremierToFinie(lieu_stock,Integer.parseInt(id_premier),Integer.parseInt(id_finie),Double.valueOf(quantite));
    }
}
