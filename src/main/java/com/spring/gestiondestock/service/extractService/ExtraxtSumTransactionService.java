package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.repositories.extractRepository.ExtractSumTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ExtraxtSumTransactionService {
    private static ExtractSumTransactionRepository extractSumTransactionRepository;
    public ExtraxtSumTransactionService(ExtractSumTransactionRepository extractSumTransactionRepository) {
        this.extractSumTransactionRepository = new ExtractSumTransactionRepository();
    }
    public Double SumTransactionEnter() throws SQLException, ClassNotFoundException {
        return extractSumTransactionRepository.SumTransactionEnter();
    }
    public Double SumTransactionExit() throws SQLException, ClassNotFoundException {
        return extractSumTransactionRepository.SumTransactionExit();
    }
}
