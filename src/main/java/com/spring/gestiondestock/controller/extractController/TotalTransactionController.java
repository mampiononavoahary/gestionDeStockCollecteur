package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.service.extractService.TotalTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/transactions/total")
public class TotalTransactionController {
    private static TotalTransactionService totalTransactionService;
    public TotalTransactionController(TotalTransactionService totalTransactionService) {
        this.totalTransactionService = totalTransactionService;
    }
    @GetMapping("/enter")
    public int enterTotalTransaction() throws SQLException, ClassNotFoundException {
        return totalTransactionService.totalTransactionEnter();
    }
    @GetMapping("/exit")
    public int exitTotalTransaction() throws SQLException, ClassNotFoundException {
        return  totalTransactionService.totalTransactionExit();
    }
}
