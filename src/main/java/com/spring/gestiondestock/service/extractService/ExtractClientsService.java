package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractClients;
import com.spring.gestiondestock.repositories.extractRepository.ExtractClientRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractClientsService {
    private static ExtractClientRepositories extractClientRepositories;

    public ExtractClientsService(ExtractClientRepositories extractClientRepositories) {
        ExtractClientsService.extractClientRepositories = extractClientRepositories;
    }

    public List<ExtractClients> getExtractClients(String query, int currentPage) throws SQLException, ClassNotFoundException {
        return extractClientRepositories.getExtractClients(query, currentPage);
    }
}
