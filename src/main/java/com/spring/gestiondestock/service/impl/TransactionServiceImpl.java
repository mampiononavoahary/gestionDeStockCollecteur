package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.exceptions.ErrorException;
import com.spring.gestiondestock.mappers.TransactionMapper;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.repositories.impl.DetailTransactionRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.StockRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.TransactionRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl {
    private static TransactionRepositoriesImpl transactionRepositories;
    private static StockRepositoriesImpl stockRepositories;
    private static DetailTransactionRepositoriesImpl detailTransactionRepositories;
    private static TransactionMapper transactionMapper;
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    public TransactionServiceImpl(TransactionRepositoriesImpl transactionRepositories, TransactionMapper transactionMapper, StockRepositoriesImpl stockRepositories, DetailTransactionRepositoriesImpl detailTransactionRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories) {
        TransactionServiceImpl.transactionRepositories = transactionRepositories;
        TransactionServiceImpl.transactionMapper = transactionMapper;
        TransactionServiceImpl.stockRepositories = stockRepositories;
        TransactionServiceImpl.detailTransactionRepositories = detailTransactionRepositories;
        TransactionServiceImpl.produitAvecDetailRepositories = produitAvecDetailRepositories;
    }
    public List<TransactionResponse> saveAllTransactions(List<TransactionRequest> transactionRequests) throws SQLException, ClassNotFoundException {
        var detail = detailTransactionRepositories.findById(transactionRequests.get(0).getId_detail_transaction());


        // Vérifiez le type de transaction et effectuez les mises à jour appropriées
        if (detail.getType_de_transaction() == TypeDeTransaction.ENTRE && Objects.equals(transactionRequests.get(0).getStatus(), "PAYE")) {
            Stock stock = stockRepositories.findByLieuAndProduit(transactionRequests.get(0).getLieu_stock(), transactionRequests.get(0).getId_produit_avec_detail());
            ProduitAvecDetail produitAvecDetail = produitAvecDetailRepositories.findProduitAvecDetailById(transactionRequests.get(0).getId_produit_avec_detail());
            if (stock == null) {
        // Créer un nouveau stock si aucun n'existe
        stock = new Stock();
        stock.setLieu_de_transaction(LieuDeTransaction.valueOf(transactionRequests.get(0).getLieu_stock()));
        stock.setProduitAvecDetail(produitAvecDetail);
        stock.setQuantite_stock(transactionRequests.get(0).getQuantite());
        stock.setUnite(Unite.valueOf(transactionRequests.get(0).getUnite()));
        
        // Sauvegarder le nouveau stock
        stockRepositories.saveStock(stock);
    }
            // Vérifiez si les lieux correspondent
            if (!stock.getLieu_de_transaction().name().equals(transactionRequests.get(0).getLieu_stock())) {
                System.out.println(stock.getLieu_de_transaction().name());
                throw new ErrorException("Le lieu de stock spécifié est introuvable.");
            }
            if (Objects.equals(transactionRequests.get(0).getUnite(), "T")) {

                stock.setQuantite_stock(stock.getQuantite_stock() + transactionRequests.get(0).getQuantite()*1000);
                stockRepositories.updateStock(stock, stock.getId_stock());
            } else if (Objects.equals(transactionRequests.get(0).getUnite(), "KG")) {
                // Met à jour le stock
                stock.setQuantite_stock(stock.getQuantite_stock() + transactionRequests.get(0).getQuantite());
                stockRepositories.updateStock(stock, stock.getId_stock());
            }
        } else if (detail.getType_de_transaction() == TypeDeTransaction.SORTIE) {
  Stock stock = stockRepositories.findByLieuAndProduit(transactionRequests.get(0).getLieu_stock(), transactionRequests.get(0).getId_produit_avec_detail());
            if (Objects.equals(transactionRequests.get(0).getUnite(), "T")) {
                double quantiteChange = transactionRequests.get(0).getQuantite()*1000;

                if (stock.getQuantite_stock()<quantiteChange) {
                    throw new ErrorException("Quantité insuffisante en stock.");
                }
            }
            // Vérifiez si les lieux correspondent
            if (!stock.getLieu_de_transaction().name().equals(transactionRequests.get(0).getLieu_stock())) {
                throw new ErrorException("Le lieu de stock spécifié est introuvable.");
            }

            // Vérifiez la quantité disponible
            if (stock.getQuantite_stock() < transactionRequests.get(0).getQuantite()) {
                throw new ErrorException("Quantité insuffisante en stock.");
            }
            if (Objects.equals(transactionRequests.get(0).getUnite(), "T")) {

                stock.setQuantite_stock(stock.getQuantite_stock() - transactionRequests.get(0).getQuantite()*1000);
                stockRepositories.updateStock(stock, stock.getId_stock());
            }else if (Objects.equals(transactionRequests.get(0).getUnite(), "KG")) {

                stock.setQuantite_stock(stock.getQuantite_stock() - transactionRequests.get(0).getQuantite());
                stockRepositories.updateStock(stock, stock.getId_stock());
            }

        }

        // Sauvegarde les transactions
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionRequest transactionRequest : transactionRequests) {
            var toEntity = transactionMapper.toEntity(transactionRequest);
            var saved = transactionRepositories.saveTransaction(toEntity);

            transactionResponses.add(transactionMapper.toResponse(saved));
        }
        return transactionResponses;
    }
    public List<TransactionResponse> saveAllTransactionsWithTransformation(List<TransactionRequest> transactionRequests) throws SQLException, ClassNotFoundException {
        var detail = detailTransactionRepositories.findById(transactionRequests.get(0).getId_detail_transaction());


        // Vérifiez le type de transaction et effectuez les mises à jour appropriées
        if (detail.getType_de_transaction() == TypeDeTransaction.ENTRE && Objects.equals(transactionRequests.get(0).getStatus(), "PAYE")) {
            Stock stock = stockRepositories.findByLieuAndProduit(transactionRequests.get(0).getLieu_stock(), transactionRequests.get(0).getId_produit_avec_detail());
            ProduitAvecDetail produitAvecDetail = produitAvecDetailRepositories.findProduitAvecDetailById(transactionRequests.get(0).getId_produit_avec_detail());
            if (stock == null) {
                // Créer un nouveau stock si aucun n'existe
                stock = new Stock();
                stock.setLieu_de_transaction(LieuDeTransaction.valueOf(transactionRequests.get(0).getLieu_stock()));
                stock.setProduitAvecDetail(produitAvecDetail);
                stock.setQuantite_stock(transactionRequests.get(0).getQuantite());
                stock.setUnite(Unite.valueOf(transactionRequests.get(0).getUnite()));

                // Sauvegarder le nouveau stock
                stockRepositories.saveStock(stock);
            }
            // Vérifiez si les lieux correspondent
            if (!stock.getLieu_de_transaction().name().equals(transactionRequests.get(0).getLieu_stock())) {
                System.out.println(stock.getLieu_de_transaction().name());
                throw new ErrorException("Le lieu de stock spécifié est introuvable.");
            }
            if (Objects.equals(transactionRequests.get(0).getUnite(), "T")) {

                stock.setQuantite_stock(stock.getQuantite_stock() + transactionRequests.get(0).getQuantite()*1000);
                stockRepositories.updateStock(stock, stock.getId_stock());
            } else if (Objects.equals(transactionRequests.get(0).getUnite(), "KG")) {
                // Met à jour le stock
                stock.setQuantite_stock(stock.getQuantite_stock() + transactionRequests.get(0).getQuantite());
                stockRepositories.updateStock(stock, stock.getId_stock());
            }
        }
        // Sauvegarde les transactions
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionRequest transactionRequest : transactionRequests) {
            var toEntity = transactionMapper.toEntity(transactionRequest);
            var saved = transactionRepositories.saveTransaction(toEntity);

            transactionResponses.add(transactionMapper.toResponse(saved));
        }
        return transactionResponses;
    }
    public Transaction deleteTransaction(int id_transaction) throws SQLException, ClassNotFoundException {
    return transactionRepositories.deleteTransaction(id_transaction);
}

}
