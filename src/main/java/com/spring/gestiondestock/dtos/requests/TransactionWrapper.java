package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionWrapper {
    private List<TransactionRequest> transactionRequests;
    private DetailTransactionRequest detailTransactionRequest;
}
