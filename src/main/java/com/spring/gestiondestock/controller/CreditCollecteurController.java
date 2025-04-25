package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.model.CreditCollecteur;
import com.spring.gestiondestock.service.CreditCollecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collecteur/credit")
public class CreditCollecteurController {
    private final CreditCollecteurService creditCollecteurService;

    @Autowired
    public CreditCollecteurController(CreditCollecteurService creditCollecteurService) {
        this.creditCollecteurService = creditCollecteurService;
    }

    @GetMapping
    public List<CreditCollecteur> findAllCreditCollecteur() {
        return creditCollecteurService.findAllCreditCollecteur();
    }

    @PostMapping
    public CreditCollecteur saveCreditCollecteur(@RequestBody CreditCollecteur creditCollecteur) {
        return creditCollecteurService.saveCreditCollecteur(creditCollecteur);
    }
    @GetMapping("/{id}")
    public Optional<CreditCollecteur> findCreditCollecteurById(@PathVariable Long id) {
        return creditCollecteurService.findCreditCollecteurById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCreditCollecteur(@PathVariable Long id) {
        creditCollecteurService.deleteCreditCollecteur(id);
    }
}
