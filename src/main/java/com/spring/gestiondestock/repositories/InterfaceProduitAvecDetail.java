package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceProduitAvecDetail<T>{
    List<T> getProduitAvecDetail() throws SQLException, ClassNotFoundException;
    List<T> createProduitAvecDetails(List<T> toSave) throws SQLException, ClassNotFoundException;
    T updateProduitAvecDetail(T toUpdate, int id) throws SQLException, ClassNotFoundException;
    T findProduitAvecDetailById(int id) throws SQLException, ClassNotFoundException;
    T deleteProduitAvecDetailById(int id) throws SQLException, ClassNotFoundException;
    T saveProduitAvecDetail(T toSave) throws SQLException, ClassNotFoundException;
}
