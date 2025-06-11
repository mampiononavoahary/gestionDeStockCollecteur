package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractBilanCreditsCollecteur;
import com.spring.gestiondestock.repositories.extractRepository.ExtractBilanCreditsRepositories;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractBilanCreditsCollecteurService {
    private final ExtractBilanCreditsRepositories extractBilanCreditsRepositories;

    public ExtractBilanCreditsCollecteurService(ExtractBilanCreditsRepositories extractBilanCreditsRepositories) {
        this.extractBilanCreditsRepositories = extractBilanCreditsRepositories;
    }
    public List<ExtractBilanCreditsCollecteur> findAll(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        return extractBilanCreditsRepositories.findAll(startDate, endDate);
    }
}
