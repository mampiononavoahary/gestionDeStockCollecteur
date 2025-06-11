package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.dtos.requests.DebitWrapper;
import com.spring.gestiondestock.dtos.responses.ProduitsCollecterResponse;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.service.ProduitsCollecterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update/{id_produit_collecter}")
    public void updateProduitsCollecter(@PathVariable int id_produit_collecter, @RequestParam Double quantite, @RequestParam String unite, @RequestParam Double prix_unitaire) throws SQLException, ClassNotFoundException {
        produitsCollecterService.updateProduitsCollecter(quantite, Unite.valueOf(unite),prix_unitaire,id_produit_collecter);
    }
    @DeleteMapping("/delete/{id_produit_collecter}")
    public void deleteProduitsCollecter(@PathVariable Long id_produit_collecter) {
        produitsCollecterService.deleteProduitsCollecter(id_produit_collecter);
    }
}
