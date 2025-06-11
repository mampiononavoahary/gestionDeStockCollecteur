package com.spring.gestiondestock.repositories;

import com.spring.gestiondestock.model.CreditCollecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterfaceCreditCollecteur extends JpaRepository<CreditCollecteur, Long> {
    @Override
    List<CreditCollecteur> findAll();

    @Override
    CreditCollecteur save(CreditCollecteur creditCollecteur);

    @Override
    Optional<CreditCollecteur> findById(Long aLong);

    List<CreditCollecteur> findByCollecteur_IdCollecteur(Long idCollecteur);

    List<CreditCollecteur> findByReferanceCredit(String referanceCredit);

    CreditCollecteur findTopByCollecteurIdCollecteurOrderByDateDeCreditDescIdCreditCollecteurDesc(Long idCollecteur);



}
