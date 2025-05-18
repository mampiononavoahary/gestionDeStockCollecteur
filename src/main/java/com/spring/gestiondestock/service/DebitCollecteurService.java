package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.repositories.InterfaceCollecteur;
import com.spring.gestiondestock.repositories.InterfaceDebitCollecteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitCollecteurService {
    private final InterfaceDebitCollecteur interfaceDebitCollecteur;

    @Autowired
    public DebitCollecteurService(InterfaceDebitCollecteur interfaceDebitCollecteur) {
        this.interfaceDebitCollecteur = interfaceDebitCollecteur;
    }

    public List<DebitCollecteur> findAll(){
        return interfaceDebitCollecteur.findAll();
    }

    public Optional<DebitCollecteur> findById(Long id){
        return interfaceDebitCollecteur.findById(id);
    }

    public DebitCollecteur saveDebitCollecteur(DebitCollecteur debitCollecteur){
        return interfaceDebitCollecteur.save(debitCollecteur);
    }

    public void deleteDebit(Long id){
        interfaceDebitCollecteur.deleteById(id);
    }
}
