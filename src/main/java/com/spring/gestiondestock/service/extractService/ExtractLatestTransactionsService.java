package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractLatestTransaction;
import com.spring.gestiondestock.repositories.extractRepository.ExtractLatestTransactionRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractLatestTransactionsService {
private static ExtractLatestTransactionRepositories extractLatestTransactionRepositories;
    public ExtractLatestTransactionsService(ExtractLatestTransactionRepositories extractLatestTransactionRepositories) {
        ExtractLatestTransactionsService.extractLatestTransactionRepositories = extractLatestTransactionRepositories;
    }

    public List<ExtractLatestTransaction> findAll() throws SQLException, ClassNotFoundException {
        return extractLatestTransactionRepositories.getLatestTransactions();
    }
}
