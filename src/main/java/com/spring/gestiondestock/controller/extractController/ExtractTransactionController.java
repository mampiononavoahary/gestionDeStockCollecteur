package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import com.spring.gestiondestock.service.extractService.ExtractTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class ExtractTransactionController {

    private final ExtractTransactionService extractTransactionService;

    public ExtractTransactionController(ExtractTransactionService extractTransactionService) {
        this.extractTransactionService = extractTransactionService;
    }

    @GetMapping
    public List<ExtractTransaction> getTransactionsExtract(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "1") int currentPage
    ) throws SQLException, ClassNotFoundException {
        return extractTransactionService.findFilteredTransactions(query, currentPage);
    }
    @GetMapping("/ventes")
    public List<ExtractTransaction> getTransactionsExtractVente(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "1") int currentPage
    ) throws SQLException, ClassNotFoundException {
        return extractTransactionService.findFilteredTransactionsVente(query, currentPage);
    }
    @GetMapping("/achats")
    public List<ExtractTransaction> getTransactionsExtractAchat(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "1") int currentPage
    ) throws SQLException, ClassNotFoundException {
        return extractTransactionService.findFilteredTransactionsAchat(query, currentPage);
    }
 
 
    @GetMapping("/count")
    public int getTransactionsExtractCount(@RequestParam(required = false, defaultValue = "") String query) throws SQLException, ClassNotFoundException {
        return extractTransactionService.countTransactions(query);
    }
}
