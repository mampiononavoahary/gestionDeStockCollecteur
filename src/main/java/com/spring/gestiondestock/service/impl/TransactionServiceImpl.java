package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.mappers.TransactionMapper;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.repositories.impl.TransactionRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl {
    private static TransactionRepositoriesImpl transactionRepositories;
    private static TransactionMapper transactionMapper;
    public TransactionServiceImpl(TransactionRepositoriesImpl transactionRepositories, TransactionMapper transactionMapper) {
        TransactionServiceImpl.transactionRepositories = transactionRepositories;
        TransactionServiceImpl.transactionMapper = transactionMapper;
    }
    public List<TransactionResponse> saveAllTransactions(List<TransactionRequest> transactionRequests) throws SQLException, ClassNotFoundException {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionRequest transactionRequest : transactionRequests) {
            var toEntity = transactionMapper.toEntity(transactionRequest);
            var saved = transactionRepositories.saveTransaction(toEntity);

            transactionResponses.add(transactionMapper.toResponse(saved));
        }
        return transactionResponses;
    }

}
