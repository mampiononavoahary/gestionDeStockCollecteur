package com.spring.gestiondestock.repositories.extractRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.CreditsExtract;
import com.spring.gestiondestock.model.extractModel.DebitsExtract;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@Repository
public class ExtractCreditsWithDebitsRepositories {
    private static Connection connection;
    private final DataSource dataSource; 
    public ExtractCreditsWithDebitsRepositories(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private CreditsExtract creditsExtract(ResultSet rs) throws SQLException {
        CreditsExtract creditsExtract = new CreditsExtract();

        creditsExtract.setId_credit_collecteur(rs.getInt("id_credit_collecteur"));
        creditsExtract.setReferance_credit(rs.getString("referance_credit"));
        creditsExtract.setDescription(rs.getString("description"));
        creditsExtract.setStatus(rs.getBoolean("status"));

        Timestamp dateCreditTimestamp = rs.getTimestamp("date_de_credit");
        if (dateCreditTimestamp != null) {
            creditsExtract.setDate_de_credit(dateCreditTimestamp.toLocalDateTime());
        }

        creditsExtract.setMontant_credit(rs.getDouble("montant_credit"));
        creditsExtract.setTotal_depense(rs.getDouble("total_depense"));
        creditsExtract.setTotal_debit(rs.getDouble("total_debit"));
        creditsExtract.setReste(rs.getDouble("reste"));

        String debitsJson = rs.getString("debit_extract");
        if (debitsJson != null && !debitsJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                List<DebitsExtract> debitsList = objectMapper.readValue(
                        debitsJson,
                        new TypeReference<List<DebitsExtract>>() {}
                );
                creditsExtract.setDebit_extract(debitsList);
            } catch (Exception e) {
                throw new SQLException("Erreur lors du parsing de debits : " + e.getMessage(), e);
            }
        }

        return creditsExtract;
    }

    public List<CreditsExtract> getCreditsWithDebits(int id_collecteur) throws SQLException, ClassNotFoundException {
        String sql = "SELECT json_agg(\n" +
                "               json_build_object(\n" +
                "                       'id_credit_collecteur', cr.id_credit_collecteur,\n" +
                "                       'referance_credit', cr.referance_credit,\n" +
                "                       'description', cr.description,\n" +
                "                       'status', cr.status,\n" +
                "                       'date_de_credit', cr.date_de_credit,\n" +
                "                       'montant_credit', cr.montant,\n" +
                "                       'recentreste', cr.recentreste,\n" +
                "                       'total_debit', COALESCE(total_data.total_debit, 0),\n" +
                "                       'total_depense', COALESCE(depense_data.total_depense, 0),\n" +
                "                       'reste', cr.montant + COALESCE(cr.recentreste, 0) - COALESCE(total_data.total_debit, 0) - COALESCE(depense_data.total_depense, 0),\n" +
                "                       'debit_extract', COALESCE(debit_data.debit_extract, '[]')\n" +
                "               )\n" +
                "       ORDER BY cr.date_de_credit DESC, cr.id_credit_collecteur DESC\n" +
                "       ) AS credit_extract\n" +
                "FROM credit_collecteur cr\n" +
                "         LEFT JOIN (\n" +
                "    SELECT\n" +
                "        d.id_credit_collecteur,\n" +
                "        json_agg(\n" +
                "                json_build_object(\n" +
                "                        'id_debit_collecteur', d.id_debit_collecteur,\n" +
                "                        'date_de_debit', d.date_de_debit,\n" +
                "                        'lieu_de_collection', d.lieu_de_collection,\n" +
                "                        'depense', d.depense,\n" +
                "                        'description', d.description,\n" +
                "                        'produits_collecter_extract', COALESCE(prod.produits_collecter_extract, '[]')\n" +
                "                )\n" +
                "        ) AS debit_extract\n" +
                "    FROM debit_collecteur d\n" +
                "             LEFT JOIN (\n" +
                "        SELECT\n" +
                "            pc.id_debit_collecteur,\n" +
                "            json_agg(\n" +
                "                    json_build_object(\n" +
                "                            'id_produit_collecter', pc.id_produits_collecter,\n" +
                "                            'id_produit_avec_detail', pc.id_produit_avec_detail,\n" +
                "                            'nom_detail', dp.nom_detail,\n" +
                "                            'quantite', pc.quantite,\n" +
                "                            'unite', pc.unite,\n" +
                "                            'prix_unitaire', pc.prix_unitaire,\n" +
                "                            'lieu_stock', pc.lieu_stock,\n" +
                "                            'total',\n" +
                "                            CASE WHEN pc.unite = 'T' THEN pc.quantite * 1000 * pc.prix_unitaire\n" +
                "                                 ELSE pc.quantite * pc.prix_unitaire\n" +
                "                                END\n" +
                "                    )\n" +
                "            ) AS produits_collecter_extract\n" +
                "        FROM produits_collecter pc\n" +
                "JOIN produit_avec_detail pd ON pc.id_produit_avec_detail = pd.id_produit_avec_detail\n" +
                "JOIN detail_produit dp ON pd.id_detail_produit = dp.id_detail_produit\n" +
                "GROUP BY pc.id_debit_collecteur\n" +
                "    ) prod ON d.id_debit_collecteur = prod.id_debit_collecteur\n" +
                "    GROUP BY d.id_credit_collecteur\n" +
                ") AS debit_data ON cr.id_credit_collecteur = debit_data.id_credit_collecteur\n" +
                "LEFT JOIN (\n" +
                "    SELECT\n" +
                "        cr.id_credit_collecteur,\n" +
                "        SUM(CASE WHEN pc.unite = 'T' THEN pc.quantite * 1000 * pc.prix_unitaire\n" +
                "                 ELSE pc.quantite * pc.prix_unitaire END) AS total_debit\n" +
                "    FROM credit_collecteur cr\n" +
                "    JOIN debit_collecteur d ON cr.id_credit_collecteur = d.id_credit_collecteur\n" +
                "    JOIN produits_collecter pc ON d.id_debit_collecteur = pc.id_debit_collecteur\n" +
                "    GROUP BY cr.id_credit_collecteur\n" +
                ") AS total_data ON cr.id_credit_collecteur = total_data.id_credit_collecteur\n" +
                "LEFT JOIN (\n" +
                "    SELECT id_credit_collecteur, SUM(depense) AS total_depense\n" +
                "    FROM debit_collecteur\n" +
                "    GROUP BY id_credit_collecteur\n" +
                ") AS depense_data ON cr.id_credit_collecteur = depense_data.id_credit_collecteur\n" +
                "WHERE cr.id_collecteur = ?;";
        List<CreditsExtract> creditsExtracts = new ArrayList<>();
        try (
        Connection connection = dataSource.getConnection();     
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id_collecteur);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String jsonArray = resultSet.getString("credit_extract");
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    try {
                        creditsExtracts = objectMapper.readValue(
                                jsonArray,
                                new TypeReference<List<CreditsExtract>>() {}
                        );
                        for (CreditsExtract credit : creditsExtracts) {
                            if (credit.getReste() <= 0) {
                                credit.setStatus(true);
                            }
                        }
                    } catch (Exception e) {
                        throw new SQLException("Erreur lors du parsing JSON de credit_extract : " + e.getMessage(), e);
                    }
                }
            }
            return creditsExtracts;
        }catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        }
    }
    public List<CreditsExtract> getCreditsWithDebitNoId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT json_agg(\n" +
                "               json_build_object(\n" +
                "                       'id_credit_collecteur', cr.id_credit_collecteur,\n" +
                "                       'referance_credit', cr.referance_credit,\n" +
                "                       'description', cr.description,\n" +
                "                       'status', cr.status,\n" +
                "                       'date_de_credit', cr.date_de_credit,\n" +
                "                       'montant_credit', cr.montant,\n" +
                "                       'recentreste', cr.recentreste,\n" +
                "                       'total_debit', COALESCE(total_data.total_debit, 0),\n" +
                "                       'total_depense', COALESCE(depense_data.total_depense, 0),\n" +
                "                       'reste', cr.montant + COALESCE(cr.recentreste, 0) - COALESCE(total_data.total_debit, 0) - COALESCE(depense_data.total_depense, 0),\n" +
                "                       'debit_extract', COALESCE(debit_data.debit_extract, '[]')\n" +
                "               )\n" +
                "       ORDER BY cr.date_de_credit DESC, cr.id_credit_collecteur DESC\n" +
                "       ) AS credit_extract\n" +
                "FROM credit_collecteur cr\n" +
                "         LEFT JOIN (\n" +
                "    SELECT\n" +
                "        d.id_credit_collecteur,\n" +
                "        json_agg(\n" +
                "                json_build_object(\n" +
                "                        'id_debit_collecteur', d.id_debit_collecteur,\n" +
                "                        'date_de_debit', d.date_de_debit,\n" +
                "                        'lieu_de_collection', d.lieu_de_collection,\n" +
                "                        'depense', d.depense,\n" +
                "                        'description', d.description,\n" +
                "                        'produits_collecter_extract', COALESCE(prod.produits_collecter_extract, '[]')\n" +
                "                )\n" +
                "        ) AS debit_extract\n" +
                "    FROM debit_collecteur d\n" +
                "             LEFT JOIN (\n" +
                "        SELECT\n" +
                "            pc.id_debit_collecteur,\n" +
                "            json_agg(\n" +
                "                    json_build_object(\n" +
                "                            'id_produit_collecter', pc.id_produits_collecter,\n" +
                "                            'id_produit_avec_detail', pc.id_produit_avec_detail,\n" +
                "                            'nom_detail', dp.nom_detail,\n" +
                "                            'quantite', pc.quantite,\n" +
                "                            'unite', pc.unite,\n" +
                "                            'prix_unitaire', pc.prix_unitaire,\n" +
                "                            'lieu_stock', pc.lieu_stock,\n" +
                "                            'total',\n" +
                "                            CASE WHEN pc.unite = 'T' THEN pc.quantite * 1000 * pc.prix_unitaire\n" +
                "                                 ELSE pc.quantite * pc.prix_unitaire\n" +
                "                                END\n" +
                "                    )\n" +
                "            ) AS produits_collecter_extract\n" +
                "        FROM produits_collecter pc\n" +
                "JOIN produit_avec_detail pd ON pc.id_produit_avec_detail = pd.id_produit_avec_detail\n" +
                "JOIN detail_produit dp ON pd.id_detail_produit = dp.id_detail_produit\n" +
                "GROUP BY pc.id_debit_collecteur\n" +
                "    ) prod ON d.id_debit_collecteur = prod.id_debit_collecteur\n" +
                "    GROUP BY d.id_credit_collecteur\n" +
                ") AS debit_data ON cr.id_credit_collecteur = debit_data.id_credit_collecteur\n" +
                "LEFT JOIN (\n" +
                "    SELECT\n" +
                "        cr.id_credit_collecteur,\n" +
                "        SUM(CASE WHEN pc.unite = 'T' THEN pc.quantite * 1000 * pc.prix_unitaire\n" +
                "                 ELSE pc.quantite * pc.prix_unitaire END) AS total_debit\n" +
                "    FROM credit_collecteur cr\n" +
                "    JOIN debit_collecteur d ON cr.id_credit_collecteur = d.id_credit_collecteur\n" +
                "    JOIN produits_collecter pc ON d.id_debit_collecteur = pc.id_debit_collecteur\n" +
                "    GROUP BY cr.id_credit_collecteur\n" +
                ") AS total_data ON cr.id_credit_collecteur = total_data.id_credit_collecteur\n" +
                "LEFT JOIN (\n" +
                "    SELECT id_credit_collecteur, SUM(depense) AS total_depense\n" +
                "    FROM debit_collecteur\n" +
                "    GROUP BY id_credit_collecteur\n" +
                ") AS depense_data ON cr.id_credit_collecteur = depense_data.id_credit_collecteur;";
        List<CreditsExtract> creditsExtracts = new ArrayList<>();
        try (
        Connection connection = dataSource.getConnection();     
        PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String jsonArray = resultSet.getString("credit_extract");
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    try {
                        creditsExtracts = objectMapper.readValue(
                                jsonArray,
                                new TypeReference<List<CreditsExtract>>() {}
                        );
                        for (CreditsExtract credit : creditsExtracts) {
                            if (credit.getReste() <= 0) {
                                credit.setStatus(true);
                            }
                        }
                    } catch (Exception e) {
                        throw new SQLException("Erreur lors du parsing JSON de credit_extract : " + e.getMessage(), e);
                    }
                }
            }
            return creditsExtracts;
        }
    }
}
