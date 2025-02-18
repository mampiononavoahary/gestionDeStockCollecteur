package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DetailProduit;
import com.spring.gestiondestock.model.Produit;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.repositories.InterfacecProduit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ProduitRepositoriesImpl implements InterfacecProduit<Produit> {
    private static Connection connection;
    private static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }

    private Produit extractProduitFromResultSet(ResultSet resultSet) throws SQLException {
        int id_produit = resultSet.getInt("id_produit");
        String nom_produit = resultSet.getString("nom_produit");
        List<ProduitAvecDetail> produitAvecDetails = new ArrayList<>();
        return new Produit(id_produit, nom_produit,produitAvecDetails);
    }
    @Override
    public List<Produit> getProduits() throws SQLException, ClassNotFoundException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                produits.add(extractProduitFromResultSet(rs));
            }
            for (Produit produit : produits) {
                System.out.println(produit);
            }
        }
        return produits;
    }

    @Override
    public Produit getProduit(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM produit WHERE id_produit=?;";
        getConnection();
        Produit produit = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return extractProduitFromResultSet(rs);
            }
        }
        return produit;
    }

    @Override
    public Produit createProduit(Produit produit) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO produit (nom_produit) VALUES (?) RETURNING id_produit;";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produit.getNom_produit());

            // Utilise executeQuery() ici aussi
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int generatedID = resultSet.getInt(1);
                produit.setId_produit(generatedID);
                log.info("Produit inséré avec ID: {}", generatedID);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }



    @Override
    public Produit updateProduit(Produit produit, int id_produit) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE produit SET nom_produit=? WHERE id_produit=?;";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, produit.getNom_produit());
            ps.setInt(2, id_produit);
            int rows = ps.executeUpdate();
            if (rows == 1) {
                log.info("produit with id : {} updated",id_produit);
            }
        }
        return produit;
    }

    @Override
    public List<Produit> createProduits(List<Produit> produits) throws SQLException, ClassNotFoundException {
        List<Produit> produitList = new ArrayList<>();
        String sql = "INSERT INTO produit (nom_produit) VALUES (?);";
        getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Produit produit : produits) {
                ps.setString(1, produit.getNom_produit());
                int rows = ps.executeUpdate();
                if (rows > 1) {
                    produitList.add(produit);
                    log.info("product saved");
                }
            }
        }
        return produitList;
    }

    @Override
    public Produit getProduitByName(String nom_produit) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM produit WHERE nom_produit=?;";
        getConnection();
        Produit produit = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nom_produit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return extractProduitFromResultSet(rs);
            }
        }
        return produit;
    }

    @Override
    public Produit deleteProduit(int id_produit) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM produit WHERE id_produit=?;";
        getConnection();
        Produit produit = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_produit);
            int rows = ps.executeUpdate();
            if (rows == 1) {
                log.info("product with id : {} deleted",id_produit);
            }
        }
        return produit;
    }
}
