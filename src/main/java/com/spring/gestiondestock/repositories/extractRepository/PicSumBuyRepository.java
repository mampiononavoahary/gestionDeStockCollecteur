package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.PicSumBuy;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PicSumBuyRepository {
    private static Connection connection;
    public static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private PicSumBuy extractResultSet(ResultSet resultSet) throws SQLException {
        Double sum = resultSet.getDouble("sum");
        String month = resultSet.getString("month");

        return new PicSumBuy(sum, month);
    }
    public List<PicSumBuy> findAll() throws SQLException, ClassNotFoundException {
        List<PicSumBuy> picSumBuyList = new ArrayList<>();
        String query = "SELECT\n" +
                "    TO_CHAR(dt.date_de_transaction, 'Month') AS month,\n" +
                "    SUM(t.prix_unitaire * CASE \n" +
            " WHEN t.unite = 'T' THEN t.quantite * 1000 \n" +
            " ELSE t.quantite \n" + 
        " END) AS sum\n" +
                "FROM\n" +
                "    transactions t\n" +
                "        JOIN\n" +
                "    produit_avec_detail pad ON t.id_produit_avec_detail = pad.id_produit_avec_detail\n" +
                "        JOIN\n" +
                "    detail_produit dp ON pad.id_detail_produit = dp.id_detail_produit\n" +
                "        JOIN\n" +
                "    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction\n" +
                "WHERE\n" +
                "    dt.type_de_transaction= 'SORTIE'-- Facultatif : Filtrer uniquement les transactions payées\n" +
                "GROUP BY\n" +
                "    month\n" +
                "ORDER BY\n" +
                "    month;";
        getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                picSumBuyList.add(extractResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        if (picSumBuyList.isEmpty()) {
            throw new SQLException("No data found for the given query.");
        }
        return picSumBuyList;
    }
}
