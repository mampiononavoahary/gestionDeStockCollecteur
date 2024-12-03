package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import com.spring.gestiondestock.repositories.extractRepository.ExtractTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractTransactionService {

    private final ExtractTransactionRepository extractTransactionRepository;

    public ExtractTransactionService(ExtractTransactionRepository extractTransactionRepository) {
        this.extractTransactionRepository = extractTransactionRepository;
    }

    public List<ExtractTransaction> findFilteredTransactions(String query, int currentPage)
            throws SQLException, ClassNotFoundException {
        return extractTransactionRepository.findFilteredTransactions(query, currentPage);
    }
    public int countTransactions(String query) throws SQLException, ClassNotFoundException {
        return extractTransactionRepository.countFilteredTransactions(query);
    }
}

