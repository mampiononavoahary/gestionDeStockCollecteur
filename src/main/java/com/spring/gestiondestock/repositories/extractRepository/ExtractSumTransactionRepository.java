package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ExtractSumTransactionRepository {
    private static Connection connection;

    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    public Double SumTransactionEnter(String lieu, Date date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "    SUM(t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END) AS sum " +
                     "FROM " +
                     "    transactions t " +
                     "    JOIN detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction " +
                     "    JOIN produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail " +
                     "    JOIN detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit " +
                     "WHERE " +
                     "    dt.type_de_transaction = 'SORTIE' " +
                     "    AND t.status = 'PAYE' " +
                     "    AND COALESCE(dt.lieu_de_transaction::TEXT, '') ILIKE ? " +
                     (date != null ? " AND DATE(dt.date_de_transaction) = ?" : "");
        
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + lieu + "%");
            if (date != null) {
                ps.setDate(2, date);
            }
            System.out.println("Executing SQL: " + ps); // Log de débogage
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("sum");
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0.0;
    }

    public Double SumTransactionExit(String lieu, Date date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "    SUM(t.prix_unitaire * CASE WHEN t.unite='T' THEN t.quantite * 1000 ELSE t.quantite END) AS sum " +
                     "FROM " +
                     "    transactions t " +
                     "    JOIN detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction " +
                     "    JOIN produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail " +
                     "    JOIN detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit " +
                     "WHERE " +
                     "    dt.type_de_transaction = 'ENTRE' " +
                     "    AND t.status = 'PAYE' " +
                     "    AND COALESCE(dt.lieu_de_transaction::TEXT, '') ILIKE ? " +
                     (date != null ? " AND DATE(dt.date_de_transaction) = ?" : "");
        
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + lieu + "%");
            if (date != null) {
                ps.setDate(2, date);
            }
            System.out.println("Executing SQL: " + ps); // Log de débogage
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("sum");
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0.0;
    }
}

