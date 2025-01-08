package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class TotalTransactionRepository {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

 
public int getTotalTransactionByDateRange() throws SQLException, ClassNotFoundException {
    String sql = "SELECT COUNT(*) AS totalTransaction " +
                 "FROM transactions " +
                 "INNER JOIN detail_transaction ON transactions.id_detail_transaction = detail_transaction.id_detail_transaction " +
                 "WHERE detail_transaction.type_de_transaction = 'SORTIE' ";
    
    getConnection();

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                return resultSet.getInt("totalTransaction");
            }
        }
    }
    return 0;
}
    public int getTotalTransactionOut() throws SQLException, ClassNotFoundException {
        String sql = "select COUNT(*) as totalTransaction from transactions INNER JOIN detail_transaction ON transactions.id_detail_transaction = detail_transaction.id_detail_transaction " +
                "WHERE detail_transaction.type_de_transaction='ENTRE';";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("totalTransaction");
            }
        }
        return 0;
    }
}
