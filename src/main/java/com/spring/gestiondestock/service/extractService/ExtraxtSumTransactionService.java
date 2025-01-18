package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.repositories.extractRepository.ExtractSumTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;

@Service
public class ExtraxtSumTransactionService {
    private static ExtractSumTransactionRepository extractSumTransactionRepository;
    public ExtraxtSumTransactionService(ExtractSumTransactionRepository extractSumTransactionRepository) {
        this.extractSumTransactionRepository = new ExtractSumTransactionRepository();
    }
    public Double SumTransactionEnter(String lieu, Date date) throws SQLException, ClassNotFoundException {
        return extractSumTransactionRepository.SumTransactionEnter(lieu, date);
    }
    public Double SumTransactionExit(String lieu, Date date) throws SQLException, ClassNotFoundException {
        return extractSumTransactionRepository.SumTransactionExit(lieu, date);
    }
}
