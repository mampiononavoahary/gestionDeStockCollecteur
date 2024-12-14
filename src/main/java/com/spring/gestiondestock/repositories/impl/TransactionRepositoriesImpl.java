package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DetailTransaction;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Transaction;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.Unite;
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
        Status status = Status.valueOf(resultSet.getString("status"));

        return new Transaction(id_transaction,id_produit_avec_detail,id_detail_transaction,quantite,unite,status);
    }
    public List<Transaction> saveAllTransaction(List<Transaction> transactions) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transactions (id_produit_avec_detail,id_detail_transaction,quantite,unite,status) VALUES (?,?,?,CAST(? AS unite),CAST(? AS status)";
        List<Transaction> transactionList = new ArrayList<>();
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Transaction transaction : transactions) {
                ps.setInt(1,transaction.getProduitAvecDetail().getId_produit_avec_detail());
                ps.setInt(2,transaction.getDetailTransaction().getId_detail_transaction());
                ps.setDouble(3,transaction.getQuantite());
                ps.setString(4,transaction.getUnite().name());
                ps.setString(5,transaction.getStatus().name());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    transactionList.add(transaction);
                }
            }
        }
        return transactionList;
    }
    public  Transaction saveTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transactions(id_produit_avec_detail,id_detail_transaction,quantite,unite,status) VALUES (?,?,?,CAST(? AS unite),CAST(? AS status));";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,transaction.getProduitAvecDetail().getId_produit_avec_detail());
            ps.setInt(2,transaction.getDetailTransaction().getId_detail_transaction());
            ps.setDouble(3,transaction.getQuantite());
            ps.setString(4,transaction.getUnite().name());
            ps.setString(5,transaction.getStatus().name());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Save transaction successful");
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
      }      
      return transaction;
    }
}
