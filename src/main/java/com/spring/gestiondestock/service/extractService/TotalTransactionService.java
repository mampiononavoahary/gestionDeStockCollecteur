package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.repositories.extractRepository.TotalTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;

@Service
public class TotalTransactionService {
    private static TotalTransactionRepository totalTransactionRepository;
    public TotalTransactionService(TotalTransactionRepository totalTransactionRepository) {
        this.totalTransactionRepository = totalTransactionRepository;
    }

    public int totalTransactionEnter() throws SQLException, ClassNotFoundException {
        return totalTransactionRepository.getTotalTransactionByDateRange();
    }
    public int totalTransactionExit() throws SQLException, ClassNotFoundException {
        return totalTransactionRepository.getTotalTransactionOut();
    }
}
