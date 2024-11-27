package com.spring.gestiondestock.dtos.responses;

import com.spring.gestiondestock.model.DetailTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientsResponse {
    private int id_clients;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private List<DetailTransaction> detailTransactions;
}
