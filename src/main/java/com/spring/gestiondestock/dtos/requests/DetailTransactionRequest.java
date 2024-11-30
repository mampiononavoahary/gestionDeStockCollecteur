package com.spring.gestiondestock.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailTransactionRequest {
    private int id_detail_transaction;
    private String type_de_transaction;
    private Timestamp date_de_transaction;
    private String Lieu_de_transaction;
    private int id_client;
}
