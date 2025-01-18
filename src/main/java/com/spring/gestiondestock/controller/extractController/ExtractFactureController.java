package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractFacture;
import com.spring.gestiondestock.service.extractService.ExtractFactureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/facture")
public class ExtractFactureController {
    private static ExtractFactureService extractFactureService;

    public ExtractFactureController(ExtractFactureService extractFactureService) {
        ExtractFactureController.extractFactureService = extractFactureService;
    }

    @GetMapping
    public List<ExtractFacture> findAll() throws SQLException, ClassNotFoundException {
        return extractFactureService.getAllFacture();
    }
}
