package com.spring.gestiondestock.model;

import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.LieuTransaction;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class DetailTransaction implements Serializable {
    private int id_detail_transaction;
    private TypeDeTransaction typeDeTransaction;
    private Timestamp date_transaction;
    private LieuTransaction lieu_de_transaction;
    private Clients id_client;
    private List<Transaction> list_transaction;
}
