package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import com.spring.gestiondestock.repositories.extractRepository.ExtractTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractTransactionService {
    private static ExtractTransactionRepository extractTransactionRepository;
    public ExtractTransactionService(ExtractTransactionRepository extractTransactionRepository) {
        this.extractTransactionRepository = extractTransactionRepository;
    }

    public List<ExtractTransaction> findAll() throws SQLException, ClassNotFoundException {
        return extractTransactionRepository.findAll();
    }
}
