package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.*;
import com.spring.gestiondestock.model.extractModel.ExtractProduitWitDetail;
import com.spring.gestiondestock.repositories.InterfaceProduitAvecDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
        int id = resultSet.getInt("id_produit_avec_detail");
        Produit produit = Produit.builder().id_produit(resultSet.getInt("id_produit")).build();
        TypeProduit typeProduit = TypeProduit.builder().id_type_produit(resultSet.getInt("id_type_produit")).build();
        DetailProduit detailProduit = DetailProduit.builder().id_detail_produit(resultSet.getInt("id_detail_produit")).build();
        List<Transaction> transactionList = new ArrayList<>();
        List<Stock> stockList = new ArrayList<>();

        return new ProduitAvecDetail(id, produit,typeProduit, detailProduit,transactionList,stockList);
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
        String sql = "insert into produit_avec_detail(produit_id,id_type_produit, detail_produit_id) values(?,?,?)";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (ProduitAvecDetail produitAvecDetail : toSave) {
                preparedStatement.setInt(1, produitAvecDetail.getId_produit().getId_produit());
                preparedStatement.setInt(2,produitAvecDetail.getId_type_produit().getId_type_produit());
                preparedStatement.setInt(3,produitAvecDetail.getId_detail_produit().getId_detail_produit());

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
        String sql = "update produit_avec_detail set id_produit = ?, id_detail_produit = ? where id_produit_avec_detail = ?";
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
        String sql = "SELECT * FROM produit_avec_detail WHERE id_produit_avec_detail = ?";
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
        String sql = "DELETE FROM produit_avec_detail WHERE id_produit_avec_detail = ?";
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
        String sql = "INSERT INTO produit_avec_detail (id_produit, id_type_produit, id_detail_produit) VALUES (?, ?, ?)";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, toSave.getId_produit().getId_produit());

            // Assurez-vous que null est bien passé pour id_type_produit
            if (toSave.getId_type_produit() != null) {
                preparedStatement.setInt(2, toSave.getId_type_produit().getId_type_produit());
            } else {
                preparedStatement.setNull(2, Types.INTEGER); // Assurez-vous que null est transmis
            }

            preparedStatement.setInt(3, toSave.getId_detail_produit().getId_detail_produit());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                log.info("produit_avec_detail saved: {}", rows);
            }
        }
        return toSave;
    }

    private ExtractProduitWitDetail extractProduitWitDetail(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_produit_avec_detail");
        String nom_detail = resultSet.getString("nom_detail");
        String categorie = resultSet.getString("categorie_produit");

        return new ExtractProduitWitDetail(id, nom_detail,categorie);
    }
    public List<ExtractProduitWitDetail> getIdAndNameDetail() throws SQLException, ClassNotFoundException {
        String sql = "select pad.id_produit_avec_detail, dp.nom_detail,dp.categorie_produit from produit_avec_detail pad INNER JOIN produit p " +
                "ON pad.id_produit = p.id_produit INNER JOIN detail_produit dp " +
                "ON pad.id_detail_produit = dp.id_detail_produit;";
        List<ExtractProduitWitDetail> listProduitWitDetail = new ArrayList<>();
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listProduitWitDetail.add(extractProduitWitDetail(resultSet));
            }
        }
        return listProduitWitDetail;
    }
    public ProduitAvecDetail findByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM produit_avec_detail pda inner join detail_produit dp on pda.id_detail_produit = dp.id_detail_produit WHERE dp.nom_detail = ?";
        getConnection();
        ProduitAvecDetail produitAvecDetail = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extract(resultSet);
            }
        }
        return produitAvecDetail;
    }
}
