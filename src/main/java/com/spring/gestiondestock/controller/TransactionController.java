package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.service.impl.TransactionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class TransactionController {
    private static TransactionServiceImpl transactionService;
    public TransactionController(TransactionServiceImpl transactionService) {
        TransactionController.transactionService = transactionService;
    }
    @PostMapping("/api/transactions/create")
    public List<TransactionResponse> createTransaction(@RequestBody List<TransactionRequest> transactionRequests) throws SQLException, ClassNotFoundException {
        return transactionService.saveAllTransactions(transactionRequests);
    }
    @DeleteMapping("/api/transactions/delete/{id_transaction}")
    public Transaction deleteTransaction(@PathVariable String id_transaction)  throws SQLException, ClassNotFoundException {
       return transactionService.deleteTransaction(Integer.parseInt(id_transaction));
    }
}
