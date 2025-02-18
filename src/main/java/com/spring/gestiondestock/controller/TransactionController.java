package com.spring.gestiondestock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gestiondestock.dtos.requests.DetailTransactionRequest;
import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.requests.TransactionWrapper;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.service.impl.TransactionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {
    private static TransactionServiceImpl transactionService;
    public TransactionController(TransactionServiceImpl transactionService) {
        TransactionController.transactionService = transactionService;
    }
    @PostMapping("/api/transactions/create")
    public List<TransactionResponse> createTransaction(@RequestBody TransactionWrapper transactionWrapper) throws SQLException, ClassNotFoundException {
        return transactionService.saveAllTransactions(transactionWrapper.getTransactionRequests(), transactionWrapper.getDetailTransactionRequest());
    }
    @GetMapping("/api/transactions/find/{id_transaction}")
    public Transaction findTransaction(@PathVariable String id_transaction) throws SQLException, ClassNotFoundException {
        return transactionService.findById(Integer.parseInt(id_transaction));
    }
    @DeleteMapping("/api/transactions/delete/{id_transaction}")
    public Transaction deleteTransaction(@PathVariable String id_transaction)  throws SQLException, ClassNotFoundException {
       return transactionService.deleteTransaction(Integer.parseInt(id_transaction));
    }
    @PutMapping("/api/transactions/update/{id_transaction}")
    public void updateTransaction(@PathVariable String id_transaction, @RequestBody String statusJson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map statusMap = objectMapper.readValue(statusJson, Map.class);
        String status = (String) statusMap.get("status");

        transactionService.updateStatusTransaction(Integer.parseInt(id_transaction), status);
    }
}
