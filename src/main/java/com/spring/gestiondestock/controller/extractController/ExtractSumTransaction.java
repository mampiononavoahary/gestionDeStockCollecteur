package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.service.extractService.ExtraxtSumTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/transactions/sum")
public class ExtractSumTransaction {
    private static ExtraxtSumTransactionService extraxtSumTransactionService;
    public ExtractSumTransaction(ExtraxtSumTransactionService extraxtSumTransactionService) {
        this.extraxtSumTransactionService = extraxtSumTransactionService;
    }
    @GetMapping("/enter")
    public Double SumTransactionEnter() throws SQLException, ClassNotFoundException {
        return extraxtSumTransactionService.SumTransactionEnter();
    }
    @GetMapping("/exit")
    public Double SumTransactionExit() throws SQLException, ClassNotFoundException {
        return extraxtSumTransactionService.SumTransactionExit();
    }
}
