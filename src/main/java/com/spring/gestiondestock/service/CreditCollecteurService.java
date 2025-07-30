package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.CreditCollecteur;
import com.spring.gestiondestock.repositories.CustomCreditCollecteurRepository;
import com.spring.gestiondestock.repositories.InterfaceCreditCollecteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCollecteurService {
    private final InterfaceCreditCollecteur interfaceCreditCollecteur;
    private final CustomCreditCollecteurRepository customRepo;

    @Autowired
    public CreditCollecteurService(InterfaceCreditCollecteur interfaceCreditCollecteur, CustomCreditCollecteurRepository customRepo) {
        this.interfaceCreditCollecteur = interfaceCreditCollecteur;
        this.customRepo = customRepo;
    }

    @Transactional
    public List<CreditCollecteur> findAllCreditCollecteur() {
        return interfaceCreditCollecteur.findAll();
    }

    @Transactional
    public CreditCollecteur saveCreditCollecteur(CreditCollecteur creditCollecteur) {
        return interfaceCreditCollecteur.save(creditCollecteur);
    }

    @Transactional
    public CreditCollecteur saveCreditWithReste(CreditCollecteur creditCollecteur) {
        return customRepo.saveWithReste(creditCollecteur);
    }

    @Transactional
    public void deleteCreditCollecteur(Long id) {
        interfaceCreditCollecteur.deleteById(id);
    }

    @Transactional
    public Optional<CreditCollecteur> findCreditCollecteurById(Long id) {
        return interfaceCreditCollecteur.findById(id);
    }

    @Transactional
    public List<CreditCollecteur> findByIdCollecteur(Long id) {
        return interfaceCreditCollecteur.findByCollecteur_IdCollecteur(id);
    }

    @Transactional
    public List<CreditCollecteur> findByReferanceCredit(String referanceCredit) {
        return interfaceCreditCollecteur.findByReferanceCredit(referanceCredit);
    }

    @Transactional
    public CreditCollecteur findTheLastCredit(Long idCollecteur) {
        return interfaceCreditCollecteur.findTopByCollecteurIdCollecteurOrderByDateDeCreditDescIdCreditCollecteurDesc(idCollecteur);
    }
}
