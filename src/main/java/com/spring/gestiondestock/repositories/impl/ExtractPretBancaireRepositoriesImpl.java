package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.extractModel.ExtractPretBancaire;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtractPretBancaireRepositoriesImpl {
    private static Connection connection;
    public void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private ExtractPretBancaire ExtractPret(ResultSet rs) throws SQLException {
        ExtractPretBancaire pretBancaireResponse = new ExtractPretBancaire();
        pretBancaireResponse.setId_pret_bancaire(rs.getInt("id_pret_bancaire"));
        pretBancaireResponse.setDate_de_pret(rs.getObject("date_de_pret", LocalDateTime.class));
        pretBancaireResponse.setProduit(rs.getString("nom_produit"));
        pretBancaireResponse.setQuantite(rs.getDouble("quantite"));
        pretBancaireResponse.setUnite(rs.getString("unite"));
        pretBancaireResponse.setPrix(rs.getDouble("prix_unitaire"));
        pretBancaireResponse.setTaux_augmentation(rs.getDouble("taux_augmentation"));
        pretBancaireResponse.setTaux_mensuel(rs.getDouble("taux_mensuel"));
        pretBancaireResponse.setDate_de_remboursement(rs.getObject("date_de_remboursement", LocalDateTime.class));

        return pretBancaireResponse;

    }
    public List<ExtractPretBancaire> allPret() throws SQLException, ClassNotFoundException {
        String sql = "select p.nom_produit,pb.* from pret_bancaire pb inner join produit p on p.id_produit = pb.produit;";
        getConnection();
        List<ExtractPretBancaire> pret = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                pret.add(ExtractPret(resultSet));
            }
            return pret;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
