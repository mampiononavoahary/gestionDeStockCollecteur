package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractLatestTransaction;
import com.spring.gestiondestock.service.extractService.ExtractLatestTransactionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions/latest")
public class ExtractLatestTransactionController {
    private final ExtractLatestTransactionsService extractLatestTransactionsService;

    public ExtractLatestTransactionController(ExtractLatestTransactionsService extractLatestTransactionsService) {
        this.extractLatestTransactionsService = extractLatestTransactionsService;
    }
    @GetMapping
    public List<ExtractLatestTransaction> getLatestTransactions() throws SQLException, ClassNotFoundException {
        return extractLatestTransactionsService.findAll();
    }
}
