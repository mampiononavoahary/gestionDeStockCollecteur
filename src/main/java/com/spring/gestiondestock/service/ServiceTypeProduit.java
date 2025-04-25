package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.requests.TypeProduitRequest;
import com.spring.gestiondestock.dtos.responses.TypeProduitResponse;

import java.sql.SQLException;
import java.util.List;

public interface ServiceTypeProduit {
    List<TypeProduitResponse> getTypeProduit() throws SQLException, ClassNotFoundException;
    TypeProduitResponse save(TypeProduitRequest typeProduitRequest) throws SQLException, ClassNotFoundException;
}
