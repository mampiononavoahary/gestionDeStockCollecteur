package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ExtractSumTransactionRepository {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    public Double SumTransactionEnter() throws SQLException, ClassNotFoundException {
        String sql = "SELECT\n" +
                "    SUM(t.quantite * dp.prix_de_vente) AS sum\n" +
                "FROM\n" +
                "    transactions t\n" +
                "        JOIN\n" +
                "    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction\n" +
                "        JOIN\n" +
                "    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "        JOIN\n" +
                "    detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit\n" +
                "WHERE\n" +
                "    dt.type_de_transaction = 'ENTRE' AND t.status= 'PAYE'\n;";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getDouble("sum");
            }
        }
        return 0.0;
    }
    public Double SumTransactionExit() throws SQLException, ClassNotFoundException {
        String sql = "SELECT\n" +
                "    SUM(t.quantite * dp.prix_d_achat) AS sum\n" +
                "FROM\n" +
                "    transactions t\n" +
                "        JOIN\n" +
                "    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction\n" +
                "        JOIN\n" +
                "    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "        JOIN\n" +
                "    detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit\n" +
                "WHERE\n" +
                "    dt.type_de_transaction = 'SORTIE';";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getDouble("sum");
            }
        }
        return 0.0;
    }
}
