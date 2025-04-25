package com.spring.gestiondestock.service.impl;

import com.spring.gestiondestock.dtos.requests.TypeProduitRequest;
import com.spring.gestiondestock.dtos.responses.TypeProduitResponse;
import com.spring.gestiondestock.mappers.TypeProduitMapper;
import com.spring.gestiondestock.model.TypeProduit;
import com.spring.gestiondestock.repositories.impl.TypeProduitRepositoriesImpl;
import com.spring.gestiondestock.service.ServiceTypeProduit;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TypeProduitServiceImpl implements ServiceTypeProduit {
    private static TypeProduitMapper typeProduitMapper;
    private static TypeProduitRepositoriesImpl typeProduitRepositories;
    public TypeProduitServiceImpl(TypeProduitRepositoriesImpl typeProduitRepositories,TypeProduitMapper typeProduitMapper) {
        TypeProduitServiceImpl.typeProduitRepositories = typeProduitRepositories;
        TypeProduitServiceImpl.typeProduitMapper = typeProduitMapper;
    }
    @Override
    public List<TypeProduitResponse> getTypeProduit() throws SQLException, ClassNotFoundException {
        List<TypeProduitResponse> typeProduits = new ArrayList<>();
        var listTypeProduit = typeProduitRepositories.getTypeProduit();
        for (TypeProduit typeProduit : listTypeProduit) {
            typeProduits.add(typeProduitMapper.toResponse(typeProduit));
        }
        return typeProduits;
    }

    @Override
    public TypeProduitResponse save(TypeProduitRequest typeProduitRequest) throws SQLException, ClassNotFoundException {
        var toSave = typeProduitMapper.toEntity(typeProduitRequest);
        return typeProduitMapper.toResponse(typeProduitRepositories.save(toSave));
    }
}
