package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.TypeProduit;
import com.spring.gestiondestock.repositories.InterfaceTypeProduit;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TypeProduitRepositoriesImpl implements InterfaceTypeProduit <TypeProduit>{

    private static Connection connection;

    public static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    @Override
    public List<TypeProduit> getTypeProduit() throws SQLException, ClassNotFoundException {
        getConnection();
        String sql = "select * from type_produit";
        List<TypeProduit> listTypeProduit = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeQuery();
            while (preparedStatement.getResultSet().next()) {
                TypeProduit typeProduit = new TypeProduit();
                typeProduit.setId_type_produit(preparedStatement.getResultSet().getInt("id_type_produit"));
                typeProduit.setNom_type_produit(preparedStatement.getResultSet().getString("nom_type_produit"));
                typeProduit.setSymbole(preparedStatement.getResultSet().getString("symbole"));
                listTypeProduit.add(typeProduit);
            }
            return listTypeProduit;
        }
        catch (SQLException e) {
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public TypeProduit save(TypeProduit toSave) throws SQLException, ClassNotFoundException {
        getConnection();
        String sql = "insert into type_produit(nom_type_produit,symbole) values(?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, toSave.getNom_type_produit());
            preparedStatement.setString(2, toSave.getSymbole());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Erreur lors de l'exécution de la requête : " + e.getMessage(), e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return toSave;
    }
}
