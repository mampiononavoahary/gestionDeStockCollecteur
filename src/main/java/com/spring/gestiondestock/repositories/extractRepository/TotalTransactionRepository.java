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

 

public int getTotalTransactionByDateRange(String location, Date date) throws SQLException, ClassNotFoundException {
    String sql = "SELECT COUNT(*) AS totalTransaction " +
                 "FROM transactions " +
                 "INNER JOIN detail_transaction ON transactions.id_detail_transaction = detail_transaction.id_detail_transaction " +
                 "WHERE detail_transaction.type_de_transaction = 'SORTIE' " +
                 "AND COALESCE(detail_transaction.lieu_de_transaction::TEXT, '') ILIKE ? " +
                 (date != null ? "AND DATE(detail_transaction.date_de_transaction) = ?" : "");

    getConnection();

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, "%" + location + "%");
        
        if (date != null) {
            preparedStatement.setDate(2, date);
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("totalTransaction");
            }
        }
    }
    return 0;
}

    public int getTotalTransactionOut(String location, Date date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS totalTransaction " +
                 "FROM transactions " +
                 "INNER JOIN detail_transaction ON transactions.id_detail_transaction = detail_transaction.id_detail_transaction " +
                 "WHERE detail_transaction.type_de_transaction = 'ENTRE' " +
                 "AND COALESCE(detail_transaction.lieu_de_transaction::TEXT, '') ILIKE ? " +
                 (date != null ? "AND DATE(detail_transaction.date_de_transaction) = ?" : "");
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + location + "%");
                   if (date != null) {
            preparedStatement.setDate(2, date);
        }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("totalTransaction");
            }
        }
        return 0;
    }
}
