package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractEnterAndExitCount;
import org.springframework.stereotype.Repository;

import java.sql.*;

import javax.sql.DataSource;

@Repository
public class ExtractSumTransactionRepository {
    private Connection connection;
    private final DataSource dataSource;
    public ExtractSumTransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
   
    // Extraction des données depuis le ResultSet
    private ExtractEnterAndExitCount extractEnterAndExitCount(ResultSet resultSet) throws SQLException {
        double sumEntre = resultSet.getDouble("sum_entre");
        double sumSortie = resultSet.getDouble("sum_sortie");
        int totalEntre = resultSet.getInt("total_entre");
        int totalSortie = resultSet.getInt("total_sortie");

        return new ExtractEnterAndExitCount(sumEntre, sumSortie, totalEntre, totalSortie);
    }

    public ExtractEnterAndExitCount SumTransactionEnterAndExit(String lieu, Date date, Date dateDebut, Date dateFin) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT " +
                " SUM(CASE WHEN dt.type_de_transaction = 'SORTIE' THEN " +
                "  t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END " +
                " END) AS sum_sortie, " +
                " SUM(CASE WHEN dt.type_de_transaction = 'ENTRE' THEN " +
                "  t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END " +
                " END) AS sum_entre, " +
                " COUNT(CASE WHEN dt.type_de_transaction = 'SORTIE' THEN 1 END) AS total_sortie, " +
                " COUNT(CASE WHEN dt.type_de_transaction = 'ENTRE' THEN 1 END) AS total_entre " +
                " FROM transactions t " +
                " JOIN detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction " +
                " JOIN produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail " +
                " JOIN detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit " +
                " WHERE t.status = 'PAYE' " +
                " AND dt.type_de_transaction IN ('SORTIE', 'ENTRE')");

        // Ajout des filtres conditionnels
        if (lieu != null && !lieu.isEmpty()) {
             sql.append(" AND COALESCE(dt.lieu_de_transaction::TEXT, '') ILIKE ?");        }
        if (date != null) {
            sql.append(" AND DATE(dt.date_de_transaction) = ?");
        }
        if (dateDebut != null && dateFin != null) {
            sql.append(" AND DATE(dt.date_de_transaction) BETWEEN ? AND ?");
        }

        ExtractEnterAndExitCount enterAndExitCount = null;

        try (
            Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (lieu != null && !lieu.isEmpty()) {
                ps.setString(paramIndex++, "%" + lieu + "%");
            }
            if (date != null) {
                ps.setDate(paramIndex++, date);
            }
            if (dateDebut != null && dateFin != null) {
                ps.setDate(paramIndex++, dateDebut);
                ps.setDate(paramIndex++, dateFin);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    enterAndExitCount = extractEnterAndExitCount(rs);
                }
            }
        }
        return enterAndExitCount != null ? enterAndExitCount : new ExtractEnterAndExitCount(0.0, 0.0, 0, 0);
    }
}

