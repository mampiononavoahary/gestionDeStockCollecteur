package com.spring.gestiondestock.controller;

import com.spring.gestiondestock.model.Collecteur;
import com.spring.gestiondestock.service.CollecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collecteurs")
public class CollecteurController {
    private final CollecteurService collecteurService;

    @Autowired
    public CollecteurController(CollecteurService collecteurService) {
        this.collecteurService = collecteurService;
    }

    @GetMapping
    public List<Collecteur> findAllCollecteur(){
        return collecteurService.findAllCollecteur();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collecteur> getCollecteurById(@PathVariable Long id) {
        return collecteurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/save")
    public Collecteur createCollecteur(@RequestBody Collecteur collecteur) {
        return collecteurService.saveCollecteur(collecteur);
    }

    @DeleteMapping("/{id}")
    public void deleteCollecteur(@PathVariable Long id) {
        collecteurService.deleteCollecteur(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Collecteur> updateCollecteur(@PathVariable Long id, @RequestBody Collecteur collecteur) {
        return collecteurService.updateCollecteur(id,collecteur);
    }

    @GetMapping("/search")
    public List<Collecteur> searchByNom(@RequestParam String nom) {
        return collecteurService.searchCollecteursByNom(nom);
    }
}
