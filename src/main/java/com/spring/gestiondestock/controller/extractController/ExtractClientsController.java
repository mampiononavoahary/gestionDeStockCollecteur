package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractClients;
import com.spring.gestiondestock.service.extractService.ExtractClientsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/clients/extracts")
public class ExtractClientsController {
    private static ExtractClientsService extractClientsService;
    public ExtractClientsController(ExtractClientsService extractClientsService) {
        ExtractClientsController.extractClientsService = extractClientsService;
    }
  @GetMapping
    public List<ExtractClients> getExtractClients(String query, int currentPage) throws SQLException, ClassNotFoundException {
        return extractClientsService.getExtractClients(query, currentPage);
    }
}
