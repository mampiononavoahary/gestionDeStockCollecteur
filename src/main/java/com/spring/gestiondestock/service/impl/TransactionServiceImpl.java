package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.DetailTransactionRequest;
import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.exceptions.ErrorException;
import com.spring.gestiondestock.mappers.TransactionMapper;
import com.spring.gestiondestock.model.*;
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
    private static DetailTransactionServiceImpl detailTransactionService;
    private static TransactionMapper transactionMapper;
    private static ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    public TransactionServiceImpl(DetailTransactionServiceImpl detailTransactionService, TransactionRepositoriesImpl transactionRepositories, TransactionMapper transactionMapper, StockRepositoriesImpl stockRepositories, DetailTransactionRepositoriesImpl detailTransactionRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories) {
        TransactionServiceImpl.detailTransactionService = detailTransactionService;
        TransactionServiceImpl.transactionRepositories = transactionRepositories;
        TransactionServiceImpl.transactionMapper = transactionMapper;
        TransactionServiceImpl.stockRepositories = stockRepositories;
        TransactionServiceImpl.detailTransactionRepositories = detailTransactionRepositories;
        TransactionServiceImpl.produitAvecDetailRepositories = produitAvecDetailRepositories;
    }
    public List<TransactionResponse> saveAllTransactions(List<TransactionRequest> transactionRequests, DetailTransactionRequest detailTransactionRequest) throws SQLException, ClassNotFoundException {

        DetailTransactionRequest detailTransaction = new DetailTransactionRequest();
        detailTransaction.setType_de_transaction(detailTransactionRequest.getType_de_transaction());
        detailTransaction.setDate_de_transaction(detailTransactionRequest.getDate_de_transaction());
        if (detailTransactionRequest.getLieu_de_transaction() != null) {
            detailTransaction.setLieu_de_transaction(detailTransactionRequest.getLieu_de_transaction());
        } else {
            throw new IllegalArgumentException("Le champ 'Lieu_de_transaction' est requis.");
        }

        detailTransaction.setId_client(detailTransactionRequest.getId_client());

        var detailResponse = detailTransactionService.addDetailTransaction(detailTransaction);


        // Vérifiez le type de transaction et effectuez les mises à jour appropriées
        if (detailResponse.getType_de_transaction() == TypeDeTransaction.ENTRE && Objects.equals(transactionRequests.get(0).getStatus(), "PAYE")) {
            Stock stock = stockRepositories.findByLieuAndProduit(transactionRequests.get(0).getLieu_stock(), transactionRequests.get(0).getId_produit_avec_detail());
            ProduitAvecDetail produitAvecDetail = produitAvecDetailRepositories.findProduitAvecDetailById(transactionRequests.get(0).getId_produit_avec_detail());
            if (stock == null) {
        // Créer un nouveau stock si aucun n'existe
        stock = new Stock();
        stock.setLieu_de_transaction(LieuDeTransaction.valueOf(transactionRequests.get(0).getLieu_stock()));
        stock.setProduitAvecDetail(produitAvecDetail);
        if ("T".equals(transactionRequests.get(0).getUnite())) {
            double quantiteChange = transactionRequests.get(0).getQuantite() * 1000;
            stock.setQuantite_stock(quantiteChange);
        } else if ("KG".equals(transactionRequests.get(0).getUnite())) {
            double change = transactionRequests.get(0).getQuantite();
            stock.setQuantite_stock(change);
        }
        stock.setUnite(Unite.valueOf("KG"));
        
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
        } else if (detailResponse.getType_de_transaction() == TypeDeTransaction.SORTIE && Objects.equals(transactionRequests.get(0).getStatus(), "PAYE")) {
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

        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionRequest transactionRequest : transactionRequests) {

            transactionRequest.setId_detail_transaction(detailResponse.getId_detail_transaction());

            var toEntity = transactionMapper.toEntity(transactionRequest);
            var saved = transactionRepositories.saveTransaction(toEntity);

            transactionResponses.add(transactionMapper.toResponse(saved));
        }
        return transactionResponses;

    }
    public Transaction findById(int id_transaction) throws SQLException, ClassNotFoundException {
        return transactionRepositories.findById(id_transaction);
    }
    public Transaction deleteTransaction(int id_transaction) throws SQLException, ClassNotFoundException {
    return transactionRepositories.deleteTransaction(id_transaction);
}
    public void updateStatusTransaction(int id_transaction, String status) throws SQLException, ClassNotFoundException {

        Transaction transaction = transactionRepositories.findById(id_transaction);

        // Vérifier si le type de transaction est "ENTRE" et que le nouveau statut est "PAYE"
        DetailTransaction detail = detailTransactionRepositories.findById(transaction.getDetailTransaction().getId_detail_transaction());
        if (detail.getType_de_transaction() == TypeDeTransaction.ENTRE && Objects.equals(status, "PAYE")) {
            Stock stock = stockRepositories.findByLieuAndProduit(String.valueOf(transaction.getLieu_stock()), transaction.getProduitAvecDetail().getId_produit_avec_detail());
            ProduitAvecDetail produitAvecDetail = produitAvecDetailRepositories.findProduitAvecDetailById(transaction.getProduitAvecDetail().getId_produit_avec_detail());
            if (produitAvecDetail == null) {
                throw new IllegalStateException("ProduitAvecDetail introuvable pour l'ID " + transaction.getDetailTransaction().getId_detail_transaction());
            }
            if (stock == null) {
                stock = new Stock();
                stock.setLieu_de_transaction(transaction.getLieu_stock());
                stock.setProduitAvecDetail(produitAvecDetail);
                if (Objects.equals(String.valueOf(transaction.getUnite()), "T")) {
                    stock.setQuantite_stock(transaction.getQuantite() * 1000);
                } else {
                    stock.setQuantite_stock(transaction.getQuantite());
                }
                stock.setUnite(transaction.getUnite());

                stockRepositories.saveStock(stock);
            } else {

                if (Objects.equals(String.valueOf(transaction.getUnite()), "T")) {
                    stock.setQuantite_stock(stock.getQuantite_stock() + transaction.getQuantite() * 1000);
                } else {
                    stock.setQuantite_stock(stock.getQuantite_stock() + transaction.getQuantite());
                }
                stockRepositories.updateStock(stock, stock.getId_stock());
            }
        } else if (detail.getType_de_transaction() == TypeDeTransaction.SORTIE && Objects.equals(status, "PAYE")) {
            Stock stock = stockRepositories.findByLieuAndProduit(String.valueOf(transaction.getLieu_stock()), transaction.getProduitAvecDetail().getId_produit_avec_detail());

            if (stock == null) {
                throw new IllegalStateException("Le stock n'existe pas pour le produit " + transaction.getDetailTransaction().getId_detail_transaction()
                        + " au lieu " + transaction.getLieu_stock());
            }
            if (stock.getQuantite_stock()<transaction.getQuantite()){
                throw new ErrorException("Quantité insuffisante en stock.");
            }

            if (Objects.equals(String.valueOf(transaction.getUnite()), "T")) {
                stock.setQuantite_stock(stock.getQuantite_stock() - transaction.getQuantite() * 1000);
            } else {
                stock.setQuantite_stock(stock.getQuantite_stock() - transaction.getQuantite());
            }
            stockRepositories.updateStock(stock, stock.getId_stock());
        }
        transactionRepositories.updateStatusTransaction(id_transaction, status);
    }

}
