package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StockRepositoriesImpl {
    private static final Logger log = LoggerFactory.getLogger(StockRepositoriesImpl.class);
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private Stock extractStock(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_stock");
        LieuDeTransaction lieu = LieuDeTransaction.valueOf(resultSet.getString("lieu_stock"));
        ProduitAvecDetail produit = ProduitAvecDetail.builder().id_produit_avec_detail(resultSet.getInt("id_produit_avec_detail")).build();
        Double quantite = resultSet.getDouble("quantite_stock");
        Unite unite = Unite.valueOf(resultSet.getString("unite"));
        return new Stock(id,lieu,produit,quantite,unite);
    }
    public Stock findById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Stock WHERE id_produit_avec_detail = ?;";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return extractStock(resultSet);
            }
        }
        return null;
    }
    public Stock findByLieuAndProduit(String lieu,int produit) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Stock WHERE lieu_stock = ?::lieu_de_transaction AND id_produit_avec_detail = ?;";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,lieu);
            ps.setInt(2,produit);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return extractStock(resultSet);
            }
        }
        return null;
    }
    public Stock updateStock(Stock stock,int id) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Stock SET lieu_stock = ?::lieu_de_transaction, quantite_stock = ?, unite = ?::unite WHERE id_stock = ?;";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,stock.getLieu_de_transaction().name());
            ps.setDouble(2,stock.getQuantite_stock());
            ps.setString(3,stock.getUnite().name());
            ps.setInt(4,id);

            int rows = ps.executeUpdate();
            if (rows >0){
                log.info("Stock saved successfuly");
            }
        }
        return stock;
    }
    public Stock saveStock(Stock toSave) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Stock (lieu_stock,id_produit_avec_detail,quantite_stock,unite) values (CAST(? AS lieu_de_transaction),?,?,CAST(? AS unite));";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,toSave.getLieu_de_transaction().name());
            ps.setInt(2,toSave.getProduitAvecDetail().getId_produit_avec_detail());
            ps.setDouble(3,toSave.getQuantite_stock());
            ps.setString(4,toSave.getUnite().name());

            int rows = ps.executeUpdate();
            if (rows>0){
                log.info("Stock creer avec succes");
            }
        }
        return toSave;
    }
}
