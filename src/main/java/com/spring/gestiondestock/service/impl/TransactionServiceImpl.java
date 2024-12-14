package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.mappers.TransactionMapper;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.repositories.impl.DetailTransactionRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.StockRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.TransactionRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl {
    private static TransactionRepositoriesImpl transactionRepositories;
    private static StockRepositoriesImpl stockRepositories;
    private static DetailTransactionRepositoriesImpl detailTransactionRepositories;
    private static TransactionMapper transactionMapper;
    public TransactionServiceImpl(TransactionRepositoriesImpl transactionRepositories, TransactionMapper transactionMapper, StockRepositoriesImpl stockRepositories, DetailTransactionRepositoriesImpl detailTransactionRepositories) {
        TransactionServiceImpl.transactionRepositories = transactionRepositories;
        TransactionServiceImpl.transactionMapper = transactionMapper;
        TransactionServiceImpl.stockRepositories = stockRepositories;
        TransactionServiceImpl.detailTransactionRepositories = detailTransactionRepositories;
    }
    public List<TransactionResponse> saveAllTransactions(List<TransactionRequest> transactionRequests) throws SQLException, ClassNotFoundException {
        var detail = detailTransactionRepositories.findById(transactionRequests.get(0).getId_detail_transaction());

        if (detail.getType_de_transaction() == TypeDeTransaction.ENTRE) {
            Stock stock = stockRepositories.findById(transactionRequests.get(0).getId_produit_avec_detail());
            stock.setQuantite_stock(stock.getQuantite_stock() + transactionRequests.get(0).getQuantite());
            stockRepositories.updateStock(stock, stock.getId_stock());
        } else if (detail.getType_de_transaction() == TypeDeTransaction.SORTIE) {
            Stock stock = stockRepositories.findById(transactionRequests.get(0).getId_produit_avec_detail());
            if (stock.getQuantite_stock() < transactionRequests.get(0).getQuantite()) {
                throw new SQLException("Stock insuffisant");
            }
            stock.setQuantite_stock(stock.getQuantite_stock() - transactionRequests.get(0).getQuantite());
            stockRepositories.updateStock(stock, stock.getId_stock());
        }

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
