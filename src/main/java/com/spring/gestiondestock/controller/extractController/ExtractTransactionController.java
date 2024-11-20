package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import com.spring.gestiondestock.service.extractService.ExtractTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class ExtractTransactionController {
    private static ExtractTransactionService extractTransactionService;
    public ExtractTransactionController(ExtractTransactionService extractTransactionService) {
        this.extractTransactionService = extractTransactionService;
    }
    @GetMapping
    public List<ExtractTransaction> getTransactionsExtract() throws SQLException, ClassNotFoundException {
        return  extractTransactionService.findAll();
    }
}
