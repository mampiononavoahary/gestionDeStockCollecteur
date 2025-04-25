package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractPretBancaire;
import com.spring.gestiondestock.repositories.impl.ExtractPretBancaireRepositoriesImpl;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractPretBancaireService {
    private static ExtractPretBancaireRepositoriesImpl extractPretBancaireRepositories;

    public ExtractPretBancaireService(ExtractPretBancaireRepositoriesImpl extractPretBancaireRepositories) {
        ExtractPretBancaireService.extractPretBancaireRepositories = extractPretBancaireRepositories;
    }

    public List<ExtractPretBancaire> allPret() throws SQLException, ClassNotFoundException {
        return extractPretBancaireRepositories.allPret();
    }
}
