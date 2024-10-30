package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceClients<T> {
    List<T> findAll() throws SQLException, ClassNotFoundException;
    List<T> saveAll(List<T> toSave) throws SQLException, ClassNotFoundException;
    List<T> findByName(String name) throws SQLException, ClassNotFoundException;
    T findById(int id) throws SQLException, ClassNotFoundException;
    T save(T toSave) throws SQLException, ClassNotFoundException;
     T update(T toUpdate) throws SQLException, ClassNotFoundException;
     T delete(int id_clients) throws SQLException, ClassNotFoundException;

}
