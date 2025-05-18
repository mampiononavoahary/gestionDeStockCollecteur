package com.spring.gestiondestock.repositories.extractRepository;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.model.extractModel.StockWithDetail;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractStockWithDetailRepositories {
    private static Connection connection;

    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private StockWithDetail extractStockWithDetail(ResultSet resultSet) throws SQLException {
        StockWithDetail stockWithDetail = new StockWithDetail();
        stockWithDetail.setId_stock(resultSet.getInt("id_stock"));
        stockWithDetail.setLieu_stock(resultSet.getString("lieu_stock"));
        stockWithDetail.setId_produit_avec_detail(resultSet.getInt("id_produit_avec_detail"));
        stockWithDetail.setQuantite_stock(resultSet.getDouble("quantite_stock"));
        stockWithDetail.setUnite(Unite.valueOf(resultSet.getString("unite")));
        stockWithDetail.setNom_detail(resultSet.getString("nom_detail"));
        stockWithDetail.setSymbole(resultSet.getString("symbole"));
        stockWithDetail.setImage_url(resultSet.getString("image_url"));
        return stockWithDetail;
    }

    public List<StockWithDetail> findAll() throws SQLException, ClassNotFoundException {
        List<StockWithDetail> stockWithDetails = new ArrayList<>();
        String sql = "select s.id_stock,s.lieu_stock,s.id_produit_avec_detail,s.quantite_stock,s.unite,dp.nom_detail,dp.symbole,dp.image_url from stock s " +
                "inner join produit_avec_detail pdt on s.id_produit_avec_detail = pdt.id_produit_avec_detail " +
                "inner join detail_produit dp on pdt.id_detail_produit = dp.id_detail_produit;";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                stockWithDetails.add(extractStockWithDetail(resultSet));
            }
        }
        return stockWithDetails;
    }
    public StockWithDetail findByLieuAndProduit(String lieu, String produit) throws SQLException, ClassNotFoundException {
        String sql = "select s.id_stock,s.lieu_stock,s.id_produit_avec_detail,s.quantite_stock,s.unite,dp.nom_detail,dp.symbole,dp.image_url from stock s " +
                "inner join produit_avec_detail pdt on s.id_produit_avec_detail = pdt.id_produit_avec_detail " +
                "inner join detail_produit dp on pdt.id_detail_produit = dp.id_detail_produit where s.lieu_stock = ?::lieu_de_transaction AND dp.nom_detail = ?;";
        getConnection();
        StockWithDetail stockWithDetail = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lieu);
            ps.setString(2, produit);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return extractStockWithDetail(resultSet);
            }
        }
        return stockWithDetail;
    }
}
