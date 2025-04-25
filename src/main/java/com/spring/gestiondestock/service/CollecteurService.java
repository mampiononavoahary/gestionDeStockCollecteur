package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.Collecteur;
import com.spring.gestiondestock.repositories.InterfaceCollecteur;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Collecteur> searchCollecteursByNom(String nom) {
        return collectuerRepository.searchByNom(nom);
    }
}
