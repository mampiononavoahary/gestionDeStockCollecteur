package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.repositories.InterfaceDetailProduits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DetailProduitRepositoriesImpl implements InterfaceDetailProduits<DetailProduit> {
    private static Connection connection;
    private static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private DetailProduit extractDetail(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_detail_produit");
        String nom = resultSet.getString("nom_detail");
        String description = resultSet.getString("description");
        Double prix_d_achat = resultSet.getDouble("prix_d_achat");
        Double prix_de_vente = resultSet.getDouble("prix_de_vente");

        return new DetailProduit(id, nom, description, prix_d_achat, prix_de_vente);
    }
    @Override
    public List<DetailProduit> getDetailProduits() throws SQLException, ClassNotFoundException {
        List<DetailProduit> detailProduits = new ArrayList<>();
        String sql = "SELECT * FROM detail_produit";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detailProduits.add(extractDetail(resultSet));
            }
            for (DetailProduit detailProduit : detailProduits) {
                System.out.println(detailProduit);
            }
        }
        return detailProduits;
    }

    @Override
    public DetailProduit toSave(DetailProduit toSave) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO detail_produit (nom_detail,description,prix_d_achat,prix_de_vente) VALUES (?,?,?,?)";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,toSave.getNom_detail());
            statement.setString(2,toSave.getDescription());
            statement.setDouble(3,toSave.getPrix_d_achat());
            statement.setDouble(4,toSave.getPrix_de_vente());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                log.info("Rows affected: {}", rows);
                return toSave;
            }
        }
        return toSave;
    }

    @Override
    public DetailProduit toUpdate(DetailProduit toUpdate) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE detail_produit SET nom_detail=?,description=?,prix_d_achat=?,prix_de_vente=? WHERE id_detail_produit=?";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,toUpdate.getNom_detail());
            statement.setString(2,toUpdate.getDescription());
            statement.setDouble(3,toUpdate.getPrix_d_achat());
            statement.setDouble(4,toUpdate.getPrix_de_vente());
            statement.setInt(5,toUpdate.getId_detail_produit());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                log.info("detail produit mis à jours: {}", rows);
                return toUpdate;
            }
        }
        return toUpdate;
    }

    @Override
    public DetailProduit toDelete(int id_detail_produit) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM detail_produit WHERE id_detail_produit=?";
        DetailProduit detailProduit = null;
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id_detail_produit);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                log.info("detail produi with idt: {} deleted", id_detail_produit);
            }
        }
        return detailProduit;
    }

    @Override
    public DetailProduit getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM detail_produit WHERE id_detail_produit=?";
        DetailProduit detailProduit = null;
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detailProduit = extractDetail(resultSet);
                System.out.println(detailProduit);
            }
        }
        return detailProduit;
    }
}
