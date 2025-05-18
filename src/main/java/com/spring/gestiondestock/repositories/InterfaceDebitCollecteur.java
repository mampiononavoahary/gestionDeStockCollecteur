package com.spring.gestiondestock.repositories;

import com.spring.gestiondestock.model.DebitCollecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterfaceDebitCollecteur extends JpaRepository<DebitCollecteur,Long> {
    @Override
    List<DebitCollecteur> findAll();

    @Override
    DebitCollecteur save(DebitCollecteur debitCollecteur);

    @Override
    Optional<DebitCollecteur> findById(Long id);
}
