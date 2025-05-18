package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.service.DebitCollecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collecteur/debit")
public class DebitCollecteurController {
    private final DebitCollecteurService debitCollecteurService;

    @Autowired
    public DebitCollecteurController(DebitCollecteurService debitCollecteurService) {
        this.debitCollecteurService = debitCollecteurService;
    }

    @GetMapping
    public List<DebitCollecteur> findAll(){
        return debitCollecteurService.findAll();
    }
    @PostMapping("/save")
    public DebitCollecteur saveDebit(@RequestBody DebitCollecteur debitCollecteur){
        return debitCollecteurService.saveDebitCollecteur(debitCollecteur);
    }

    @GetMapping("/{id}")
    public Optional<DebitCollecteur> findById(@PathVariable Long id){
        return debitCollecteurService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDebit(@PathVariable Long id){
        debitCollecteurService.deleteDebit(id);
    }
}
