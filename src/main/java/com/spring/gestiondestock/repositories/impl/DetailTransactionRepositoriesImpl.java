package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.model.extractModel.ExtractDetailTransaction;
import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.Clients;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DetailTransactionRepositoriesImpl {
    private static final Logger log = LoggerFactory.getLogger(DetailTransactionRepositoriesImpl.class);
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private DetailTransaction extractDetailTransaction(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_detail_transaction");
        UUID uuid = UUID.fromString(resultSet.getString("uuid_detail"));
        TypeDeTransaction type_de_transaction = TypeDeTransaction.valueOf(resultSet.getString("type_de_transaction"));
        Timestamp date_de_transaction = resultSet.getTimestamp("date_de_transaction");
        LieuDeTransaction lieu_de_transaction = LieuDeTransaction.valueOf(resultSet.getString("lieu_de_transaction"));
        Clients id_client = Clients.builder().id_clients(resultSet.getInt("id_client")).build();
        List<Transaction> transactions = new ArrayList<>();

        return new DetailTransaction(id,uuid,type_de_transaction,date_de_transaction,lieu_de_transaction,id_client,transactions);
    }
    public List<DetailTransaction> findAllDetailTransaction() throws SQLException, ClassNotFoundException {
        List<DetailTransaction> detailTransactions = new ArrayList<>();
        String query = "SELECT * FROM detail_transaction";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                detailTransactions.add(extractDetailTransaction(resultSet));
            }
        }catch (SQLException e) {
            log.error("Erreur lors de l'exécution de la requête : {}", e.getMessage());
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return detailTransactions;
    }
    public DetailTransaction SaveDetailTransaction(DetailTransaction detailTransaction) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO detail_transaction(type_de_transaction,date_de_transaction,lieu_de_transaction,id_client) " +
                "VALUES(CAST(? AS type_de_transaction),?,CAST(? AS lieu_de_transaction),?) "+
        "RETURNING id_detail_transaction;";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, detailTransaction.getType_de_transaction().name());
            preparedStatement.setTimestamp(2, detailTransaction.getDate_de_transaction());
            preparedStatement.setString(3, detailTransaction.getLieu_de_transaction().name());
            preparedStatement.setInt(4, detailTransaction.getId_client().getId_clients());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id_detail_transaction");
                detailTransaction.setId_detail_transaction(generatedId);
                log.info("Detail Transaction Saved with ID: " + generatedId);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return detailTransaction;
    }
    public DetailTransaction findById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM detail_transaction WHERE id_detail_transaction = ?";
        DetailTransaction detailTransaction = null;
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                detailTransaction = extractDetailTransaction(resultSet);
            }
        }catch (SQLException e) {
            log.error("Erreur lors de la récupération du détail de transaction : {}", e.getMessage());
            throw new SQLException("Erreur lors de la récupération du détail de transaction : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return detailTransaction;
    }
    public DetailTransaction findByTypeDeTransaction(TypeDeTransaction type_de_transaction) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM detail_transaction WHERE type_de_transaction = ?";
        DetailTransaction detailTransaction = null;
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(TypeDeTransaction.valueOf(String.valueOf(type_de_transaction))));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                detailTransaction = extractDetailTransaction(resultSet);
            }
        }
        catch (SQLException e) {
            log.error("Erreur lors de la récupération du détail de transaction par type : {}", e.getMessage());
            throw new SQLException("Erreur lors de la récupération du détail de transaction par type : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return detailTransaction;
    }
    private ExtractDetailTransaction extractDetailTransaction2(ResultSet resultSet2) throws SQLException {
        ExtractDetailTransaction extractDetailTransaction = new ExtractDetailTransaction();
        extractDetailTransaction.setId_detail_transaction(resultSet2.getInt("id_detail_transaction"));
        extractDetailTransaction.setNom(resultSet2.getString("nom"));
        extractDetailTransaction.setPrenom(resultSet2.getString("prenom"));

        return extractDetailTransaction;
    }
    public List<ExtractDetailTransaction> lastDetailTransaction() throws SQLException, ClassNotFoundException {
        String sql = "SELECT dt.*,c.nom,c.prenom FROM detail_transaction dt INNER JOIN clients c ON dt.id_client = c.id_clients ORDER BY id_detail_transaction DESC LIMIT 4";
        getConnection();
        List<ExtractDetailTransaction> detail = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                detail.add(extractDetailTransaction2(resultSet));
            }
        }
        catch (SQLException e) {
            log.error("Erreur lors de l'exécution de la requête : {}", e.getMessage());
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
      return detail;
    }
}
