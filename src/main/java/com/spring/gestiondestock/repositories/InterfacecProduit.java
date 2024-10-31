package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfacecProduit<T>{
    List<T> getProduits() throws SQLException, ClassNotFoundException;
    T getProduit(int id) throws SQLException, ClassNotFoundException;
    T createProduit(T produit) throws SQLException, ClassNotFoundException;
    T updateProduit(T produit, int id_produit) throws SQLException, ClassNotFoundException;
    List<T> createProduits(List<T> produits) throws SQLException, ClassNotFoundException;
    T getProduitByName(String nom_produit) throws SQLException, ClassNotFoundException;
    T deleteProduit(int id_produit) throws SQLException, ClassNotFoundException;
}
