package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.TotalTransaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TotalTransactionRepository {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    public int getTotalTransactionEnter() throws SQLException, ClassNotFoundException {
        String sql = "select COUNT(*) as totalTransaction from transactions INNER JOIN " +
                "detail_transaction ON transactions.id_detail_transaction = detail_transaction.id_detail_transaction WHERE detail_transaction.type_de_transaction='SORTIE';\n";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("totalTransaction");
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
