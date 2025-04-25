package com.spring.gestiondestock.repositories;

import com.spring.gestiondestock.model.Collecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterfaceCollecteur extends JpaRepository<Collecteur, Long> {
    @Override
    List<Collecteur> findAll();

    @Override
    Optional<Collecteur> findById(Long aLong);

    @Override
    Collecteur save(Collecteur collecteur);

    @Query("SELECT c FROM Collecteur c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Collecteur> searchByNom(@Param("nom") String nom);
}
