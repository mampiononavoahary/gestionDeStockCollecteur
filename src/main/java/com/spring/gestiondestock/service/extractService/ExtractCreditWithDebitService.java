package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.CreditsExtract;
import com.spring.gestiondestock.repositories.extractRepository.ExtractCreditsWithDebitsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractCreditWithDebitService {
    private final ExtractCreditsWithDebitsRepositories extractCreditsWithDebitsRepositories;

    @Autowired
    public ExtractCreditWithDebitService(ExtractCreditsWithDebitsRepositories extractCreditsWithDebitsRepositories) {
        this.extractCreditsWithDebitsRepositories = extractCreditsWithDebitsRepositories;
    }
    public List<CreditsExtract> getAllCreditsAndDebitsByIdCollecteur(int id_collecteur) throws SQLException, ClassNotFoundException {

        return extractCreditsWithDebitsRepositories.getCreditsWithDebits(id_collecteur);
    }
    public List<CreditsExtract> getAllCreditsAndDebits() throws SQLException, ClassNotFoundException {
        return extractCreditsWithDebitsRepositories.getCreditsWithDebitNoId();
    }
}
