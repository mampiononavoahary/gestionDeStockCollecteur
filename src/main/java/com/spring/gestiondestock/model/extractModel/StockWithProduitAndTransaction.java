package com.spring.gestiondestock.model.extractModel;

import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockWithProduitAndTransaction {
    private int id_stock;
    private String lieu_stock;
  private Double quantite_stock;
  private Unite unite_stock;
    private String nom_detail;
    private Double quantite;
    private Unite unite;
    private Status status;
    private TypeDeTransaction typeDeTransaction;
    private LieuDeTransaction lieuDeTransaction;
    private Timestamp date_transaction;
    private String nom_client;
}
