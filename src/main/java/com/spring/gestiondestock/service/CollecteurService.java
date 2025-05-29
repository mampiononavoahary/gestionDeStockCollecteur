package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.Collecteur;
import com.spring.gestiondestock.repositories.InterfaceCollecteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollecteurService {
    private final InterfaceCollecteur collectuerRepository;

    @Autowired
    public CollecteurService(InterfaceCollecteur collectuerRepository) {
        this.collectuerRepository = collectuerRepository;
    }
    public List<Collecteur> findAllCollecteur(){
        return collectuerRepository.findAll();
    }
    public Optional<Collecteur> findById(Long id){
        return collectuerRepository.findById(id);
    }
    public Collecteur saveCollecteur(Collecteur collecteur){
        return collectuerRepository.save(collecteur);
    }
    public void deleteCollecteur(Long id){
        collectuerRepository.deleteById(id);
    }
    public ResponseEntity<Collecteur> updateCollecteur(Long id, Collecteur updatedData) {
        Optional<Collecteur> optionalCollecteur = collectuerRepository.findById(id);
        if (optionalCollecteur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Collecteur existing = optionalCollecteur.get();

        existing.setNom(updatedData.getNom());
        existing.setPrenom(updatedData.getPrenom());
        existing.setAdresse(updatedData.getAdresse());
        existing.setTelephone(updatedData.getTelephone());

        Collecteur saved = collectuerRepository.save(existing);

        return ResponseEntity.ok(saved);
    }
    public List<Collecteur> searchCollecteursByNom(String nom) {
        return collectuerRepository.searchByNom(nom);
    }
}
