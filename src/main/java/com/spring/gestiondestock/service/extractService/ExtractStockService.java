package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.StockWithDetail;
import com.spring.gestiondestock.repositories.extractRepository.ExtractStockWithDetailRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractStockService {
    private static ExtractStockWithDetailRepositories extractStockWithDetailRepositories;

    public ExtractStockService(ExtractStockWithDetailRepositories extractStockWithDetailRepositories) {
        this.extractStockWithDetailRepositories = extractStockWithDetailRepositories;
    }
    public List<StockWithDetail> findAll() throws SQLException, ClassNotFoundException {
        return extractStockWithDetailRepositories.findAll();
    }
    public StockWithDetail findByLieuAndProduit(String lieu, String nom_produit) throws SQLException, ClassNotFoundException {
        return extractStockWithDetailRepositories.findByLieuAndProduit(lieu, nom_produit);
    }
}
