package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.DebitWrapper;
import com.spring.gestiondestock.dtos.responses.ProduitsCollecterResponse;
import com.spring.gestiondestock.service.ProduitsCollecterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/debit/produitscollecter")
public class ProduitsCollecterController {
    private final ProduitsCollecterServiceImpl produitsCollecterService;

    @Autowired
    public ProduitsCollecterController(ProduitsCollecterServiceImpl produitsCollecterService) {
        this.produitsCollecterService = produitsCollecterService;
    }

    @PostMapping
    public List<ProduitsCollecterResponse> saveProduitsCollecter(@RequestBody DebitWrapper debitWrapper) throws SQLException, ClassNotFoundException {
        return produitsCollecterService.saveProduitsCollecter(debitWrapper.getProduitsCollecterRequests(), debitWrapper.getDebitCollecteur());
    }
}
