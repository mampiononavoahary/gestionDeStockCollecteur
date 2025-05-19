package com.spring.gestiondestock.repositories;

import com.spring.gestiondestock.model.CreditCollecteur;

public interface CustomCreditCollecteurRepository {
    CreditCollecteur saveWithReste(CreditCollecteur creditCollecteur);
}
