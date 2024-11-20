package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractTransactionRepository {
    private static Connection connection;
    private static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private ExtractTransaction extractTransaction(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_transaction");
        String nom_client = resultSet.getString("nom_client");
        String nom_produit = resultSet.getString("nom_produit");
        Timestamp timestamp = resultSet.getTimestamp("date_transaction");
        String lieu = resultSet.getString("lieu_transaction");
        Double quantite = resultSet.getDouble("quantite");
        String unite = resultSet.getString("unite");
        String status = resultSet.getString("status");

        return new ExtractTransaction(id,nom_client,nom_produit,timestamp,lieu,quantite,unite,status);

    }
    public List<ExtractTransaction> findAll() throws SQLException, ClassNotFoundException {
        List<ExtractTransaction> extractTransactions = new ArrayList<>();
        String query = "SELECT \n" +
                "    t.id_transaction AS id_transaction, \n"+
                "    c.nom AS nom_client,\n" +
                "    p.nom_produit AS nom_produit,\n" +
                "    dt.date_de_transaction AS date_transaction,\n" +
                "    dt.lieu_de_transaction AS lieu_transaction,\n" +
                "    t.quantite AS quantite,\n" +
                "    t.unite::text AS unite,\n" +
                "    t.status::text AS status\n" +
                "FROM \n" +
                "    transactions t\n" +
                "JOIN \n" +
                "    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction\n" +
                "JOIN \n" +
                "    clients c ON dt.id_client = c.id_clients\n" +
                "JOIN \n" +
                "    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "JOIN \n" +
                "    produit p ON pad.id_produit = p.id_produit;";

        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractTransactions.add(extractTransaction(resultSet));
            }
            for (ExtractTransaction extractTransaction : extractTransactions) {
                System.out.println(extractTransaction);
            }
        }
        return  extractTransactions;
    }
}
