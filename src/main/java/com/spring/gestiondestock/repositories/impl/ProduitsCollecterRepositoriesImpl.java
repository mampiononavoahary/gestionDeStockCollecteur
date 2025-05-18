package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.ProduitsCollecter;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduitsCollecterRepositoriesImpl {
    private static Connection connection;

    public static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private ProduitsCollecter extractProduitsCollecter(ResultSet resultSet) throws SQLException {
        ProduitsCollecter produitsCollecter = new ProduitsCollecter();
        produitsCollecter.setIdProduitCollecter(resultSet.getLong("id_produit_collecter"));
        produitsCollecter.setDebitCollecteur(DebitCollecteur.builder().idDebitCollecteur(resultSet.getLong("id_debit_collecteur")).build());
        produitsCollecter.setProduitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(resultSet.getInt("id_produit_avec_detail")).build());
        produitsCollecter.setQuantite(resultSet.getDouble("quantite"));
        produitsCollecter.setUnite(Unite.valueOf(resultSet.getString("unite")));
        produitsCollecter.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
        produitsCollecter.setLieu_stock(LieuDeTransaction.valueOf(resultSet.getString("lieu_stock")));
        return produitsCollecter;
    }
    public List<ProduitsCollecter> getAllProduitsCollecter() throws SQLException, ClassNotFoundException {
        getConnection();
        String query = "SELECT * FROM produits_collecter";
        List<ProduitsCollecter> produitsCollecters = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProduitsCollecter produitsCollecter = extractProduitsCollecter(resultSet);
                produitsCollecters.add(produitsCollecter);
            }
            return produitsCollecters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ProduitsCollecter createProduitsCollecter(ProduitsCollecter produitsCollecter) throws SQLException, ClassNotFoundException {
        getConnection();
        String query = "INSERT INTO produits_collecter (id_debit_collecteur, id_produit_avec_detail, quantite, unite, prix_unitaire, lieu_stock) VALUES (?, ?, ?, CAST(? AS unite), ?, CAST(? AS lieu_de_transaction))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, produitsCollecter.getDebitCollecteur().getIdDebitCollecteur());
            preparedStatement.setInt(2, produitsCollecter.getProduitAvecDetail().getId_produit_avec_detail());
            preparedStatement.setDouble(3, produitsCollecter.getQuantite());
            preparedStatement.setString(4, produitsCollecter.getUnite().toString());
            preparedStatement.setDouble(5, produitsCollecter.getPrix_unitaire());
            preparedStatement.setString(6, produitsCollecter.getLieu_stock().toString());
            preparedStatement.executeUpdate();
            return produitsCollecter;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
