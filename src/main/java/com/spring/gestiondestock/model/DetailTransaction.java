package com.spring.gestiondestock.model;

import com.spring.gestiondestock.enums.TypeDeTransaction;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.security.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class DetailTransaction implements Serializable {
    private int id_detail_transaction;
    private TypeDeTransaction typeDeTransaction;
    private Double quantite;
    private Timestamp date_transaction;
    private String lieu_de_transaction;
    private Clients clients;
}
