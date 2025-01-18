package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.StockWithProduitAndTransaction;
import com.spring.gestiondestock.repositories.extractRepository.ExtractStockWithProduitAndTransactionRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractStockWithProduitAndTransactionService {
   private static ExtractStockWithProduitAndTransactionRepositories extractStockWithProduitAndTransactionRepositories;

    public ExtractStockWithProduitAndTransactionService(ExtractStockWithProduitAndTransactionRepositories extractStockWithProduitAndTransactionRepositories) {
        this.extractStockWithProduitAndTransactionRepositories = extractStockWithProduitAndTransactionRepositories;
   }
   public List<StockWithProduitAndTransaction> findByLieuAndProduit(String lieu, String nom_produit) throws SQLException, ClassNotFoundException {
       return extractStockWithProduitAndTransactionRepositories.findByLieuAndProduit(lieu, nom_produit);
   }
}
