package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailTransactionResponse {
    private int id_detail_transaction;
    private UUID id_detail_transaction_uuid;
    private TypeDeTransaction type_de_transaction;
    private Timestamp date_de_transaction;
    private LieuDeTransaction lieu_de_transaction;
    private Clients id_client;
    private List<Transaction> list_transaction;
}
