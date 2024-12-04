package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractLatestTransaction;
import com.spring.gestiondestock.model.extractModel.ExtractTransaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractLatestTransactionRepositories {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    public List<ExtractLatestTransaction> getLatestTransactions() throws SQLException, ClassNotFoundException {
        List<ExtractLatestTransaction> extractTransactions = new ArrayList<>();
        String sql = "select t.id_transaction,c.nom,dt.date_de_transaction,dp.nom_detail,t.quantite,t.unite\n" +
                "from transactions t INNER JOIN  detail_transaction dt\n" +
                "ON t.id_detail_transaction=dt.id_detail_transaction\n" +
                "INNER JOIN clients c\n" +
                "ON c.id_clients=dt.id_client\n" +
                "INNER JOIN produit_avec_detail pd\n" +
                "ON pd.id_produit_avec_detail=t.id_produit_avec_detail\n" +
                "INNER JOIN detail_produit dp\n" +
                "ON dp.id_detail_produit=pd.id_detail_produit ORDER BY date_de_transaction DESC LIMIT 3;";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                extractTransactions.add(extractLatestTransaction(resultSet));
            }
        }
        return extractTransactions;
    }

    private ExtractLatestTransaction extractLatestTransaction(ResultSet resultSet) {
        ExtractLatestTransaction extractLatestTransaction = new ExtractLatestTransaction();
        try {
            extractLatestTransaction.setId_transaction(resultSet.getInt("id_transaction"));
            extractLatestTransaction.setNom(resultSet.getString("nom"));
            extractLatestTransaction.setDate_de_transaction(resultSet.getTimestamp("date_de_transaction"));
            extractLatestTransaction.setNom_detail(resultSet.getString("nom_detail"));
            extractLatestTransaction.setQuantite(resultSet.getDouble("quantite"));
            extractLatestTransaction.setUnite(resultSet.getString("unite"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extractLatestTransaction;
    }
}
