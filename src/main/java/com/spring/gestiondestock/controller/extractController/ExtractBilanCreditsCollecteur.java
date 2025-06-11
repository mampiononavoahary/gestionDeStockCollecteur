package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.service.extractService.ExtractBilanCreditsCollecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/extract/bilan/credits/collecteur")
public class ExtractBilanCreditsCollecteur {
    private final ExtractBilanCreditsCollecteurService extractBilanCreditsCollecteurService;

    @Autowired
    public ExtractBilanCreditsCollecteur(ExtractBilanCreditsCollecteurService extractBilanCreditsCollecteurService) {
        this.extractBilanCreditsCollecteurService = extractBilanCreditsCollecteurService;
    }

    @GetMapping
    public List<com.spring.gestiondestock.model.extractModel.ExtractBilanCreditsCollecteur> findAll(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws SQLException, ClassNotFoundException {
       Date debutFormattedDate = null;
       Date finFormattedDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        if (startDate != null && !startDate.isBlank()) {
            debutFormattedDate = Date.valueOf(LocalDate.parse(startDate, formatter));
        }

        if (endDate != null && !endDate.isBlank()) {
            finFormattedDate = Date.valueOf(LocalDate.parse(endDate, formatter));
        }
       return extractBilanCreditsCollecteurService.findAll(debutFormattedDate, finFormattedDate);
    }
}
