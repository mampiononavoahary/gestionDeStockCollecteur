package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.repositories.extractRepository.TotalTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TotalTransactionService {
    private static TotalTransactionRepository totalTransactionRepository;
    public TotalTransactionService(TotalTransactionRepository totalTransactionRepository) {
        this.totalTransactionRepository = totalTransactionRepository;
    }

    public int totalTransactionEnter() throws SQLException, ClassNotFoundException {
        return totalTransactionRepository.getTotalTransactionEnter();
    }
    public int totalTransactionExit() throws SQLException, ClassNotFoundException {
        return totalTransactionRepository.getTotalTransactionOut();
    }
}
