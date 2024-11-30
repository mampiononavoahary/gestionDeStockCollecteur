package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.dtos.requests.TransactionRequest;
import com.spring.gestiondestock.dtos.responses.TransactionResponse;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public Transaction toEntity(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .detailTransaction(DetailTransaction.builder().id_detail_transaction(transactionRequest.getId_detail_transaction()).build())
                .produitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(transactionRequest.getId_produit_avec_detail()).build())
                .quantite(transactionRequest.getQuantite())
                .unite(Unite.valueOf(transactionRequest.getUnite()))
                .status(Status.valueOf(transactionRequest.getStatus()))
                .build();
    }
    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id_transaction(transaction.getId_transaction())
                .detailTransaction(transaction.getDetailTransaction())
                .produitAvecDetail(transaction.getProduitAvecDetail())
                .quantite(transaction.getQuantite())
                .unite(transaction.getUnite())
                .status(transaction.getStatus())
                .build();
    }
}
