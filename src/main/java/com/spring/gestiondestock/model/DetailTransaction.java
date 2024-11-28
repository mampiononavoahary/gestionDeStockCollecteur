package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class DetailTransaction implements Serializable {
    private int id_detail_transaction;
    private UUID id_detail_transaction_uuid;
    private TypeDeTransaction type_de_transaction;
    private Timestamp date_de_transaction;
    private LieuDeTransaction lieu_de_transaction;
    private Clients id_client;
    private List<Transaction> list_transaction;
}
