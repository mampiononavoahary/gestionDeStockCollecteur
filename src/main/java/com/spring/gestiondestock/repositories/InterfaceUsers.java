package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceUsers<T>{
    List<T> getAll() throws SQLException, ClassNotFoundException;
    T getById(int id);
    T Update(T toUpdate);
    T save(T toSave);
    T delete(T toDelete);
    T findByName(String name);
}
