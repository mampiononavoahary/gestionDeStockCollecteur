package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDetailProduits<T>{
    public List<T> getDetailProduits() throws SQLException, ClassNotFoundException;
    T toSave(T toSave) throws SQLException, ClassNotFoundException;
    T toUpdate(T toUpdate) throws SQLException, ClassNotFoundException;
    T toDelete(int id_detail_produit) throws SQLException, ClassNotFoundException;
    T getById(int id) throws SQLException, ClassNotFoundException;
}
