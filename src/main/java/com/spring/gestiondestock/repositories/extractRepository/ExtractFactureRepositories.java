package com.spring.gestiondestock.repositories.extractRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.LigneFacture;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.model.extractModel.ExtractFacture;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class ExtractFactureRepositories {
    private static Connection connection;
    private final DataSource dataSource;
    
    public ExtractFactureRepositories(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private ExtractFacture extractFacture(ResultSet rs) throws SQLException {
        ExtractFacture extractFacture = new ExtractFacture();
        extractFacture.setId_facture(rs.getInt("id_facture"));
        extractFacture.setDate_de_transaction(rs.getTimestamp("date_de_transaction"));
    extractFacture.setLieu_de_transaction(rs.getString("lieu_de_transaction"));
        extractFacture.setNom_client(rs.getString("nom"));
        extractFacture.setPrenom_client(rs.getString("prenom"));
        extractFacture.setAdresse_client(rs.getString("adresse"));
        extractFacture.setTelephone_client(rs.getString("telephone"));

        // Récupérer la colonne JSON sous forme de chaîne
        String lignesFactureJson = rs.getString("lignes_facture");
        if (lignesFactureJson != null && !lignesFactureJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                // Désérialiser le JSON en une liste d'objets LigneFacture
                List<LigneFacture> lignesFacture = objectMapper.readValue(
                        lignesFactureJson,
                        new TypeReference<List<LigneFacture>>() {}
                );
                extractFacture.setLignes_facture(lignesFacture);
            } catch (Exception e) {
                throw new SQLException("Erreur lors du parsing de lignes_facture : " + e.getMessage(), e);
            }
        }
        return extractFacture;
    }
    public List<ExtractFacture> getAllFacture() throws SQLException, ClassNotFoundException {
        String sql = "SELECT \n" +
                "    dt.id_detail_transaction AS id_facture,\n" +
                "    dt.date_de_transaction,\n" +
                "    dt.lieu_de_transaction,\n" +
                "    c.nom,\n" +
                "    c.prenom,\n" +
                "    c.adresse,\n" +
                "    c.telephone,\n" +
                "    JSON_AGG(\n" +
                "        JSON_BUILD_OBJECT(\n" +
                "            'produit', dp.nom_detail,\n" +
                "            'quantite', t.quantite,\n" +
                "            'unite', t.unite,\n" +
                "            'prix', t.prix_unitaire,\n" +
                "            'total', t.prix_unitaire * (\n" +
                "                CASE \n" +
                "                    WHEN t.unite = 'T' THEN t.quantite * 1000 \n" +
                "                    ELSE t.quantite \n" +
                "                END\n" +
                "            )\n" +
                "        )\n" +
                "    ) AS lignes_facture\n" +
                "FROM \n" +
                "    detail_transaction dt\n" +
                "JOIN \n" +
                "    transactions t ON dt.id_detail_transaction = t.id_detail_transaction\n" +
                "JOIN \n" +
                "    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "JOIN \n" +
                "    detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit\n" +
                "JOIN \n" +
                "    clients c ON dt.id_client = c.id_clients\n" +
                "WHERE \n" +
                "    dt.type_de_transaction = 'SORTIE'\n" +
                "    AND t.status = 'PAYE'\n" +
                "GROUP BY \n" +
                "    dt.id_detail_transaction, \n" +
                "    c.nom, \n" +
                "    c.prenom, \n" +
                "    c.adresse, \n" +
                "    c.telephone, \n" +
                "    dt.date_de_transaction, \n" +
                "    dt.lieu_de_transaction\n" +
                "ORDER BY \n" +
                "    dt.date_de_transaction DESC;";

        List<ExtractFacture> extractFactures = new ArrayList<>();
        try (
            Connection connection = dataSource.getConnection();     
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                extractFactures.add(extractFacture(rs));
            }
      for(ExtractFacture extractFacture1: extractFactures){
        System.out.println(extractFacture1);
      }
            return extractFactures;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        }
     }
}
