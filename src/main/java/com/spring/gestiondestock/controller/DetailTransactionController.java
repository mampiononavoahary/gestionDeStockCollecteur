package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.DetailTransactionRequest;
import com.spring.gestiondestock.dtos.responses.DetailTransactionResponse;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.extractModel.ExtractDetailTransaction;
import com.spring.gestiondestock.service.impl.DetailTransactionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/detailtransaction")
public class DetailTransactionController {
    private static DetailTransactionServiceImpl detailTransactionService;
    public DetailTransactionController(DetailTransactionServiceImpl detailTransactionService) {
        DetailTransactionController.detailTransactionService = detailTransactionService;
    }
    @GetMapping
    public List<DetailTransactionResponse> getAllDetailTransactions() throws SQLException, ClassNotFoundException {
        return detailTransactionService.getAllDetailTransaction();
    }
    @PostMapping("/post")
    public DetailTransactionResponse postDetailTransaction(@RequestBody DetailTransactionRequest detailTransaction) throws SQLException, ClassNotFoundException {
        return detailTransactionService.addDetailTransaction(detailTransaction);
    }
    @GetMapping("/lastdetail")
    public List<ExtractDetailTransaction> getLastDetailTransactionId() throws SQLException, ClassNotFoundException {
        return detailTransactionService.getLasteDetailTransactionId();
    }
}
