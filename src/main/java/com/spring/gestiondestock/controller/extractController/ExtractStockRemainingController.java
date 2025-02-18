package com.spring.gestiondestock.controller.extractController;

import com.spring.gestiondestock.model.extractModel.ExtractStockRemaining;
import com.spring.gestiondestock.service.extractService.ExtractStockRemainingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/stock/remaining")
public class ExtractStockRemainingController {
    private static ExtractStockRemainingService extractStockRemainingService;
    public ExtractStockRemainingController(ExtractStockRemainingService extractStockRemainingService){
        ExtractStockRemainingController.extractStockRemainingService = extractStockRemainingService;
    }

    @GetMapping
    public List<ExtractStockRemaining> AllStockRemaining() throws SQLException, ClassNotFoundException {
        return extractStockRemainingService.AllStockRemaining();
    }
}
