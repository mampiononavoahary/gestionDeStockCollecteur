package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Status;
import com.spring.gestiondestock.model.enums.TypeDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.model.extractModel.StockWithProduitAndTransaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractStockWithProduitAndTransactionRepositories {
    private static Connection connection;

    public static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    private StockWithProduitAndTransaction extract(ResultSet resultSet) throws SQLException {
        StockWithProduitAndTransaction stockWithProduitAndTransaction = new StockWithProduitAndTransaction();
        stockWithProduitAndTransaction.setId_stock(resultSet.getInt("id_stock"));
        stockWithProduitAndTransaction.setLieu_stock(resultSet.getString("lieu_stock"));
        stockWithProduitAndTransaction.setQuantite_stock(resultSet.getDouble("quantite_stock"));
        stockWithProduitAndTransaction.setUnite_stock(Unite.valueOf(resultSet.getString("unite_stock")));
        stockWithProduitAndTransaction.setNom_detail(resultSet.getString("nom_detail"));
        stockWithProduitAndTransaction.setQuantite(resultSet.getDouble("quantite"));
        stockWithProduitAndTransaction.setUnite(Unite.valueOf(resultSet.getString("unite")));
        stockWithProduitAndTransaction.setStatus(Status.valueOf(resultSet.getString("status")));
        stockWithProduitAndTransaction.setTypeDeTransaction(TypeDeTransaction.valueOf(resultSet.getString("type_de_transaction")));
        stockWithProduitAndTransaction.setLieuDeTransaction(LieuDeTransaction.valueOf(resultSet.getString("lieu_de_transaction")));
        stockWithProduitAndTransaction.setDate_transaction(resultSet.getTimestamp("date_de_transaction"));
        stockWithProduitAndTransaction.setNom_client(resultSet.getString("nom_client"));
        return stockWithProduitAndTransaction;
    }

    public List<StockWithProduitAndTransaction> findByLieuAndProduit(String lieu, String nom_produit) throws SQLException, ClassNotFoundException {
        List<StockWithProduitAndTransaction> stockWithProduitAndTransactions = new ArrayList<>();
        String sql = "SELECT \n" +
                "    s.id_stock,\n" +
                "    s.lieu_stock,\n" +
                "    s.quantite_stock,\n" +
                "    s.unite as unite_stock,\n" +
                "    p.nom_detail,\n" +
                "    t.quantite,\n" +
                "    t.unite,\n" +
                "    t.status,\n" +
                "    dt.type_de_transaction,\n" +
                "    dt.lieu_de_transaction,\n" +
                "    dt.date_de_transaction,\n" +
                "    c.nom AS nom_client\n" +
                "FROM \n" +
                "    stock s\n" +
                "JOIN \n" +
                "    produit_avec_detail pdt ON s.id_produit_avec_detail = pdt.id_produit_avec_detail\n" +
                "JOIN\n" +
                "    detail_produit p ON pdt.id_detail_produit = p.id_detail_produit\n" +
                "JOIN \n" +
                "    transactions t ON t.id_produit_avec_detail = pdt.id_produit_avec_detail\n" +
                "JOIN \n" +
                "    detail_transaction dt ON t.id_detail_transaction = dt.id_detail_transaction\n" +
                "JOIN \n" +
                "    clients c ON dt.id_client = c.id_clients where t.lieu_stock = s.lieu_stock AND s.lieu_stock = ?::lieu_de_transaction AND p.nom_detail = ?;";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lieu);
            preparedStatement.setString(2, nom_produit);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stockWithProduitAndTransactions.add(extract(resultSet));
            }
            return stockWithProduitAndTransactions;
        }
    }
}
