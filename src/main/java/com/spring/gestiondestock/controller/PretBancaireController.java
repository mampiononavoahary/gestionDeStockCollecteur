package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.model.PretBancaire;
import com.spring.gestiondestock.service.PretBancaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pret-bancaire")
public class PretBancaireController {
    private final PretBancaireService pretBancaireService;

    public PretBancaireController(PretBancaireService pretBancaireService) {
        this.pretBancaireService = pretBancaireService;
    }
    @PostMapping("/save")
    public PretBancaire save(@RequestBody PretBancaire pretBancaire) {
        return pretBancaireService.savePretBancaire(pretBancaire);
    }
    @GetMapping
    public List<PretBancaire> getPretBancaires() {
        return pretBancaireService.getPretBancaires();
    }
    @GetMapping("/{id}/final-amount")
    public ResponseEntity<Double> getFinalAmount(@PathVariable Long id) {
        double finalAmount = pretBancaireService.calculateFinalAmount(id);
        return ResponseEntity.ok(finalAmount);
    }
}
