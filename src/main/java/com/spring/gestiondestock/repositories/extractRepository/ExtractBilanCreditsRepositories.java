package com.spring.gestiondestock.repositories.extractRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractBilanCreditsCollecteur;
import com.spring.gestiondestock.model.extractModel.ExtractBilanProduitsCollecter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ExtractBilanCreditsRepositories {
    private static Connection connection;

    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    private ExtractBilanCreditsCollecteur extractResultset(ResultSet resultSet) throws SQLException {
            ExtractBilanCreditsCollecteur extractBilanCreditsCollecteur = new ExtractBilanCreditsCollecteur();
            extractBilanCreditsCollecteur.setCollecteur(resultSet.getString("nom_collecteur"));
            extractBilanCreditsCollecteur.setTotal_credit(resultSet.getDouble("total_credit"));

            String produitsJson = resultSet.getString("produits");
            if (produitsJson != null && !produitsJson.isEmpty()) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ExtractBilanProduitsCollecter> produits = objectMapper.readValue(
                            produitsJson,
                            new TypeReference<List<ExtractBilanProduitsCollecter>>() {}
                    );
                    extractBilanCreditsCollecteur.setProduits(produits);
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            return extractBilanCreditsCollecteur;
    }
    public List<ExtractBilanCreditsCollecteur> findAll(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        String dateFilter = "";
        if (startDate != null && endDate != null) {
            dateFilter = " WHERE cc.date_de_credit BETWEEN ? AND ? ";
        }


        String sql = "WITH credits AS (\n" +
                "    SELECT\n" +
                "        c.id_collecteur,\n" +
                "        c.nom AS nom_collecteur,\n" +
                "        SUM(cc.montant) AS total_credit\n" +
                "    FROM collecteur c\n" +
                "             JOIN credit_collecteur cc ON cc.id_collecteur = c.id_collecteur\n" +
                "    " + dateFilter +
                "    GROUP BY c.id_collecteur, c.nom\n" +
                "),\n" +
                "\n" +
                "     produit_aggregat AS (\n" +
                "         SELECT\n" +
                "             c.id_collecteur,\n" +
                "             dp.nom_detail AS nom_produit,\n" +
                "             pc.unite,\n" +
                "             SUM(CASE WHEN pc.unite = 'T' THEN pc.quantite * 1000 ELSE pc.quantite END) AS total_quantite\n" +
                "         FROM collecteur c\n" +
                "                  JOIN credit_collecteur cc ON cc.id_collecteur = c.id_collecteur\n" +
                "                  JOIN debit_collecteur dc ON dc.id_credit_collecteur = cc.id_credit_collecteur\n" +
                "                  JOIN produits_collecter pc ON pc.id_debit_collecteur = dc.id_debit_collecteur\n" +
                "                  JOIN produit_avec_detail pad ON pc.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "                  JOIN detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit\n" +
                "         " + dateFilter +
                "         GROUP BY c.id_collecteur, dp.nom_detail, pc.unite\n" +
                "     ),\n" +
                "\n" +
                "     produits_json AS (\n" +
                "         SELECT\n" +
                "             id_collecteur,\n" +
                "             json_agg(\n" +
                "                     json_build_object(\n" +
                "                             'nom_produit', nom_produit,\n" +
                "                             'total_quantite', total_quantite,\n" +
                "                             'unite', unite\n" +
                "                     )\n" +
                "             ) AS produits\n" +
                "         FROM produit_aggregat\n" +
                "         GROUP BY id_collecteur\n" +
                "     )\n" +
                "\n" +
                "SELECT\n" +
                "    cr.nom_collecteur,\n" +
                "    cr.total_credit,\n" +
                "    COALESCE(pj.produits, '[]'::json) AS produits\n" +
                "FROM credits cr\n" +
                "         LEFT JOIN produits_json pj ON pj.id_collecteur = cr.id_collecteur;";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (startDate != null && endDate != null) {
                preparedStatement.setDate(paramIndex++, startDate);
                preparedStatement.setDate(paramIndex++, endDate);
                preparedStatement.setDate(paramIndex++, startDate);
                preparedStatement.setDate(paramIndex++, endDate);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExtractBilanCreditsCollecteur> extractBilanCreditsCollecteurs = null;
            while (resultSet.next()) {
                if (extractBilanCreditsCollecteurs == null) {
                    extractBilanCreditsCollecteurs = new java.util.ArrayList<>();
                }
                extractBilanCreditsCollecteurs.add(extractResultset(resultSet));
            }
            return extractBilanCreditsCollecteurs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

}
