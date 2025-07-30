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
        if (connection == null || connection.isClosed()) {
            Connect connect = new Connect();
            connection = connect.CreateConnection();
        }
    }

    private ExtractTransaction extractTransaction(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_transaction");
        String nomClient = resultSet.getString("nom_client");
        String nomProduit = resultSet.getString("nom_produit");
        Timestamp dateTransaction = resultSet.getTimestamp("date_transaction");
        String lieuTransaction = resultSet.getString("lieu_de_transaction");
        Double quantite = resultSet.getDouble("quantite");
        String unite = resultSet.getString("unite");
        String status = resultSet.getString("status");

        return new ExtractTransaction(id, nomClient, nomProduit, dateTransaction, lieuTransaction, quantite, unite, status);
    }

    public List<ExtractTransaction> findFilteredTransactions(String query, int currentPage) throws SQLException, ClassNotFoundException {
        List<ExtractTransaction> extractTransactions = new ArrayList<>();
        String sql = """
            SELECT 
                t.id_transaction AS id_transaction, 
                c.nom AS nom_client,
                p.nom_produit AS nom_produit,
                dt.date_de_transaction AS date_transaction,
                dt.lieu_de_transaction::text,
                t.quantite AS quantite,
                t.unite::text AS unite,
                t.status::text AS status
            FROM 
                transactions t
            JOIN 
                detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction
            JOIN 
                clients c ON dt.id_client = c.id_clients
            JOIN 
                produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail
            JOIN 
                produit p ON pad.id_produit = p.id_produit
            WHERE 
                COALESCE(c.nom, '') ILIKE ? OR 
            COALESCE(p.nom_produit, '') ILIKE ? OR
            COALESCE(dt.lieu_de_transaction::TEXT, '') ILIKE ? OR
            COALESCE(t.status::TEXT, '') ILIKE ?          ORDER BY 
                dt.date_de_transaction DESC
            LIMIT 6 OFFSET ?;
        """;

        int offset = (currentPage - 1) * 6;

        getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");
            preparedStatement.setString(4, "%" + query + "%");
            preparedStatement.setInt(5, offset);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractTransactions.add(extractTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw e;
        }finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return extractTransactions;
    }
    public List<ExtractTransaction> findFilteredTransactionsVente(String query, int currentPage) throws SQLException, ClassNotFoundException {
        List<ExtractTransaction> extractTransactions = new ArrayList<>();
        String sql = """
            SELECT 
                t.id_transaction AS id_transaction, 
                c.nom AS nom_client,
                p.nom_produit AS nom_produit,
                dt.date_de_transaction AS date_transaction,
                dt.lieu_de_transaction::text,
                t.quantite AS quantite,
                t.unite::text AS unite,
                t.status::text AS status
            FROM 
                transactions t
            JOIN 
                detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction
            JOIN 
                clients c ON dt.id_client = c.id_clients
            JOIN 
                produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail
            JOIN 
                produit p ON pad.id_produit = p.id_produit
            WHERE 
                (c.nom ILIKE ? OR 
                p.nom_produit ILIKE ? OR
                dt.lieu_de_transaction::TEXT ILIKE ? OR
                t.status::TEXT ILIKE ?) AND
                dt.type_de_transaction = 'SORTIE'
            ORDER BY 
                dt.date_de_transaction DESC
            LIMIT 6 OFFSET ?;
        """;

        int offset = (currentPage - 1) * 6;

        getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");
            preparedStatement.setString(4, "%" + query + "%");
            preparedStatement.setInt(5, offset);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractTransactions.add(extractTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw e;
        }

        return extractTransactions;
    }
 public List<ExtractTransaction> findFilteredTransactionsAchat(String query, int currentPage) throws SQLException, ClassNotFoundException {
        List<ExtractTransaction> extractTransactions = new ArrayList<>();
        String sql = """
            SELECT 
                t.id_transaction AS id_transaction, 
                c.nom AS nom_client,
                p.nom_produit AS nom_produit,
                dt.date_de_transaction AS date_transaction,
                dt.lieu_de_transaction::text,
                t.quantite AS quantite,
                t.unite::text AS unite,
                t.status::text AS status
            FROM 
                transactions t
            JOIN 
                detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction
            JOIN 
                clients c ON dt.id_client = c.id_clients
            JOIN 
                produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail
            JOIN 
                produit p ON pad.id_produit = p.id_produit
            WHERE 
                (c.nom ILIKE ? OR 
                p.nom_produit ILIKE ? OR
                dt.lieu_de_transaction::TEXT ILIKE ? OR
                t.status::TEXT ILIKE ?) AND
                dt.type_de_transaction = 'ENTRE'
            ORDER BY 
                dt.date_de_transaction DESC
            LIMIT 6 OFFSET ?;
        """;

        int offset = (currentPage - 1) * 6;

        getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");
            preparedStatement.setString(4, "%" + query + "%");
            preparedStatement.setInt(5, offset);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractTransactions.add(extractTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw e;
        }finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return extractTransactions;
    }
 
    public int countFilteredTransactions(String query) throws SQLException, ClassNotFoundException {
        String sql = """
            SELECT 
                COUNT(*) AS total
            FROM 
                transactions t
            JOIN 
                detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction
            JOIN 
                clients c ON dt.id_client = c.id_clients
            JOIN 
                produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail
            JOIN 
                produit p ON pad.id_produit = p.id_produit
            WHERE 
                c.nom ILIKE ? OR 
                p.nom_produit ILIKE ? OR
                dt.lieu_de_transaction::TEXT ILIKE ? OR
                t.status::TEXT ILIKE ?
        """;

        getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");
            preparedStatement.setString(4, "%" + query + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw e;
        }
        return 0;
        }
}

