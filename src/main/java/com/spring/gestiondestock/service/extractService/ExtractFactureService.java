package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractFacture;
import com.spring.gestiondestock.repositories.extractRepository.ExtractFactureRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractFactureService {
    private static ExtractFactureRepositories extractFactureRepositories;

    public ExtractFactureService(ExtractFactureRepositories extractFactureRepositories) {
        this.extractFactureRepositories = extractFactureRepositories;
    }

    public List<ExtractFacture> getAllFacture() throws SQLException, ClassNotFoundException {;
        return extractFactureRepositories.getAllFacture();
    }
}
