package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.CreditCollecteur;
import com.spring.gestiondestock.repositories.InterfaceCreditCollecteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCollecteurService {
    private final InterfaceCreditCollecteur interfaceCreditCollecteur;

    @Autowired
    public CreditCollecteurService(InterfaceCreditCollecteur interfaceCreditCollecteur) {
        this.interfaceCreditCollecteur = interfaceCreditCollecteur;
    }
    public List<CreditCollecteur> findAllCreditCollecteur() {
        return interfaceCreditCollecteur.findAll();
    }

    public CreditCollecteur saveCreditCollecteur(CreditCollecteur creditCollecteur) {
        return interfaceCreditCollecteur.save(creditCollecteur);
    }

    public void deleteCreditCollecteur(Long id) {
        interfaceCreditCollecteur.deleteById(id);
    }

    public Optional<CreditCollecteur> findCreditCollecteurById(Long id) {
        return interfaceCreditCollecteur.findById(id);
    }
    public List<CreditCollecteur> findByIdCollecteur(Long id) {
        return interfaceCreditCollecteur.findByCollecteur_IdCollecteur(id);
    }
    public List<CreditCollecteur> findByReferanceCredit(String referanceCredit) {
        return interfaceCreditCollecteur.findByReferanceCredit(referanceCredit);
    }
}
