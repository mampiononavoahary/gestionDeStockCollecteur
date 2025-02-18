package com.spring.gestiondestock.service.extractService;

import com.spring.gestiondestock.model.extractModel.ExtractStockRemaining;
import com.spring.gestiondestock.repositories.extractRepository.ExtractStockRemainingRepositories;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ExtractStockRemainingService {
    private static ExtractStockRemainingRepositories extractStockRemainingRepositories;

    public ExtractStockRemainingService(ExtractStockRemainingRepositories extractStockRemainingRepositories) {
        ExtractStockRemainingService.extractStockRemainingRepositories = extractStockRemainingRepositories;
    }
    public List<ExtractStockRemaining> AllStockRemaining() throws SQLException, ClassNotFoundException {
        return extractStockRemainingRepositories.getAllStockRemaining();
    }
}
