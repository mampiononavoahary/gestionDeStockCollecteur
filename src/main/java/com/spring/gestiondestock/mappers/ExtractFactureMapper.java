package com.spring.gestiondestock.mappers;

import com.spring.gestiondestock.model.extractModel.ExtractFacture;
import org.springframework.stereotype.Component;

@Component
public class ExtractFactureMapper {
    public ExtractFacture toResponse(ExtractFacture extractFacture) {
        return  ExtractFacture.builder()
                .id_facture(extractFacture.getId_facture())
                .date_de_transaction(extractFacture.getDate_de_transaction())
                .nom_client(extractFacture.getNom_client())
                .prenom_client(extractFacture.getPrenom_client())
                .adresse_client(extractFacture.getAdresse_client())
                .telephone_client(extractFacture.getTelephone_client())
                .lignes_facture(extractFacture.getLignes_facture())
                .build();
    }
}
