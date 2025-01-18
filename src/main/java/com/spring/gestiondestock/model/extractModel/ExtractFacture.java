package com.spring.gestiondestock.model.extractModel;

import com.spring.gestiondestock.model.LigneFacture;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExtractFacture {
    private int id_facture; // Correspond à id_detail_transaction
    private Timestamp date_de_transaction;
  private String lieu_de_transaction;
    private String nom_client;
    private String prenom_client;
    private String adresse_client;
    private String telephone_client;
    private List<LigneFacture> lignes_facture; // Liste des lignes associées à la facture
}
