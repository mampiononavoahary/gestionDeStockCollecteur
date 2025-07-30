package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.Unite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Repository
public class TransactionRepositoriesImpl {
    private static final Logger log = LoggerFactory.getLogger(StockRepositoriesImpl.class);
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        int id_transaction = resultSet.getInt("id_transaction");
        ProduitAvecDetail id_produit_avec_detail = ProduitAvecDetail.builder().id_produit_avec_detail(resultSet.getInt("id_produit_avec_detail")).build();
        DetailTransaction id_detail_transaction = DetailTransaction.builder().id_detail_transaction(resultSet.getInt("id_detail_transaction")).build();
        Double quantite = resultSet.getDouble("quantite");
        Unite unite = Unite.valueOf(resultSet.getString("unite"));
        Double prix_unitaire = resultSet.getDouble("prix_unitaire");
        Status status = Status.valueOf(resultSet.getString("status"));
        LieuDeTransaction lieu_stock = LieuDeTransaction.valueOf(resultSet.getString("lieu_stock"));

        return new Transaction(id_transaction,id_produit_avec_detail,id_detail_transaction,quantite,unite,prix_unitaire,status,lieu_stock);
    }
    public List<Transaction> saveAllTransaction(List<Transaction> transactions) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transactions (id_produit_avec_detail,id_detail_transaction,quantite,unite,prix_unitaire,status,lieu_stock) VALUES (?,?,?,CAST(? AS unite),?,CAST(? AS status),CAST(? AS lieu_de_transaction));";
        List<Transaction> transactionList = new ArrayList<>();
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Transaction transaction : transactions) {
                ps.setInt(1,transaction.getProduitAvecDetail().getId_produit_avec_detail());
                ps.setInt(2,transaction.getDetailTransaction().getId_detail_transaction());
                ps.setDouble(3,transaction.getQuantite());
                ps.setString(4,transaction.getUnite().name());
                ps.setDouble(5,transaction.getPrix_unitaire());
                ps.setString(6,transaction.getStatus().name());
                ps.setString(7,transaction.getLieu_stock().name());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    transactionList.add(transaction);
                }
            }
        }catch (SQLException e) {
            log.error("Erreur lors de l'exécution de la requête : {}", e.getMessage());
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return transactionList;
    }

    public  Transaction saveTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transactions(id_produit_avec_detail,id_detail_transaction,quantite,unite,prix_unitaire,status,lieu_stock) VALUES (?,?,?,CAST(? AS unite),?,CAST(? AS status),CAST(? AS lieu_de_transaction));";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,transaction.getProduitAvecDetail().getId_produit_avec_detail());
            ps.setInt(2,transaction.getDetailTransaction().getId_detail_transaction());
            ps.setDouble(3,transaction.getQuantite());
            ps.setString(4,transaction.getUnite().name());
            ps.setDouble(5,transaction.getPrix_unitaire());
            ps.setString(6,transaction.getStatus().name());
            ps.setString(7,transaction.getLieu_stock().name());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Save transaction successful");
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
        return transaction;
    }
    public Transaction deleteTransaction(int id_transaction)  throws SQLException, ClassNotFoundException {
      String sql = "DELETE FROM transactions WHERE id_transaction=?;";
      getConnection();
      Transaction transaction = null;
      try(PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
        prepareStatement.setInt(1,id_transaction);
        int rows = prepareStatement.executeUpdate();
        if (rows == 1) {
          System.out.println("Transaction deleted");
        } 
      }catch (SQLException e) {
        log.error("Erreur lors de l'exécution de la requête : {}", e.getMessage());
        throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
      } finally {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      }      
      return transaction;
    }
    public Transaction findById(int id_transaction) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM transactions WHERE id_transaction=?;";
        getConnection();
        Transaction transaction = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id_transaction);
           ResultSet resultSet = preparedStatement.executeQuery();
           if (resultSet.next()){
               transaction = extractTransactionFromResultSet(resultSet);
           }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération de la transaction avec ID " + id_transaction, e);
        }
        finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return transaction;
    }
    public void updateStatusTransaction(int id_transaction,String status) throws SQLException, ClassNotFoundException {
      String sql = "UPDATE transactions SET status=CAST(? AS status) WHERE id_transaction=?;";
      getConnection();
      try(PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
        prepareStatement.setString(1,status);
        prepareStatement.setInt(2,id_transaction);
        int rows = prepareStatement.executeUpdate();
        if (rows == 1) {
          System.out.println("Transaction updated");
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
    }
}
