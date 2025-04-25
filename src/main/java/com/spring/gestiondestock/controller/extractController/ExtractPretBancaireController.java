package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractPretBancaire;
import com.spring.gestiondestock.service.extractService.ExtractPretBancaireService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/extract/pret-bancaire")
public class ExtractPretBancaireController {
    private static ExtractPretBancaireService extractPretBancaireService;

    public ExtractPretBancaireController(ExtractPretBancaireService extractPretBancaireService) {
        ExtractPretBancaireController.extractPretBancaireService = extractPretBancaireService;
    }
    @GetMapping
    public List<ExtractPretBancaire> allPret() throws SQLException, ClassNotFoundException {
        return extractPretBancaireService.allPret();
    }
}
