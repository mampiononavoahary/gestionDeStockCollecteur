package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.CreditsExtract;
import com.spring.gestiondestock.service.extractService.ExtractCreditWithDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/credits/debits/collecteur")
public class ExtractCreditsAndDebitController {
    private final ExtractCreditWithDebitService extractCreditsWithDebits;

    @Autowired
    public ExtractCreditsAndDebitController(ExtractCreditWithDebitService extractCreditsWithDebits) {
        this.extractCreditsWithDebits = extractCreditsWithDebits;
    }

    @GetMapping
    public List<CreditsExtract> getAll() throws SQLException, ClassNotFoundException {
        return extractCreditsWithDebits.getAllCreditsAndDebits();
    }

    @GetMapping("/{id_collecteur}")
    public List<CreditsExtract> getByIdCollecteur(@PathVariable String id_collecteur) throws SQLException, ClassNotFoundException {
        return extractCreditsWithDebits.getAllCreditsAndDebitsByIdCollecteur(Integer.parseInt(id_collecteur));
    }
}
