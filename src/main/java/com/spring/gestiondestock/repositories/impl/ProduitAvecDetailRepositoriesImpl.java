package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.repositories.InterfaceProduitAvecDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduitAvecDetailRepositoriesImpl implements InterfaceProduitAvecDetail<ProduitAvecDetail> {
    private static final Logger log = LoggerFactory.getLogger(ProduitAvecDetailRepositoriesImpl.class);
    private static Connection connection;
    private static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private ProduitAvecDetail extract(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Produit produit = new Produit();
        DetailProduit detailProduit = new DetailProduit();

        return new ProduitAvecDetail(id, produit, detailProduit);
    }
    @Override
    public List<ProduitAvecDetail> getProduitAvecDetail() throws SQLException, ClassNotFoundException {
        List<ProduitAvecDetail> listProduitAvecDetail = new ArrayList<>();
        String sql = "select * from produit_avec_detail";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProduitAvecDetail produitAvecDetail = extract(resultSet);
                listProduitAvecDetail.add(produitAvecDetail);
            }
            for (ProduitAvecDetail produit : listProduitAvecDetail) {
                System.out.println(produit);
            }
        }
        return listProduitAvecDetail;
    }

    @Override
    public List<ProduitAvecDetail> createProduitAvecDetails(List<ProduitAvecDetail> toSave) throws SQLException, ClassNotFoundException {
        List<ProduitAvecDetail> listProduitAvecDetail = new ArrayList<>();
        String sql = "insert into produit_avec_detail(produit_id, detail_produit_id) values(?,?)";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (ProduitAvecDetail produitAvecDetail : toSave) {
                preparedStatement.setInt(1, produitAvecDetail.getId_produit().getId_produit());
                preparedStatement.setInt(2,produitAvecDetail.getId_detail_produit().getId_detail_produit());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    listProduitAvecDetail.add(extract(resultSet));
                    log.info("produit avec detail saved: {}", listProduitAvecDetail);
                }
            }
        }
        return listProduitAvecDetail;
    }

    @Override
    public ProduitAvecDetail updateProduitAvecDetail(ProduitAvecDetail toUpdate, int id) throws SQLException, ClassNotFoundException {
        String sql = "update produit_avec_detail set id_produit = ?, id_detail_produit = ? where id = ?";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, toUpdate.getId_produit().getId_produit());
            preparedStatement.setInt(2, toUpdate.getId_detail_produit().getId_detail_produit());
            preparedStatement.setInt(3, toUpdate.getId_produit_avec_detail());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                log.info("produit_avec_detail updated: {}", rows);
            }
        }
        return toUpdate;
    }

    @Override
    public ProduitAvecDetail findProduitAvecDetailById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM produit_avec_detail WHERE id = ?";
        getConnection();
        ProduitAvecDetail produitAvecDetail = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extract(resultSet);
            }
        }
        return produitAvecDetail;
    }

    @Override
    public ProduitAvecDetail deleteProduitAvecDetailById(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM produit_avec_detail WHERE id = ?";
        ProduitAvecDetail produitAvecDetail = null;
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                log.info("produit_avec_detail with id {} deleted", id);
            }
        }
        return produitAvecDetail;
    }

    @Override
    public ProduitAvecDetail saveProduitAvecDetail(ProduitAvecDetail toSave) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO produit_avec_detail (id_produit, id_detail_produit) values(?,?)";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, toSave.getId_produit().getId_produit());
            preparedStatement.setInt(2, toSave.getId_detail_produit().getId_detail_produit());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                log.info("produit_avec_detail saved: {}", rows);
            }
        }
        return toSave;
    }
}
