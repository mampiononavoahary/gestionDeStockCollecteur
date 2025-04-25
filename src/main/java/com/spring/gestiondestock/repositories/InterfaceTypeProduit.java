package com.spring.gestiondestock.repositories;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceTypeProduit <T>{
    List<T> getTypeProduit() throws SQLException, ClassNotFoundException;
    T save(T toSave) throws SQLException, ClassNotFoundException;
}
