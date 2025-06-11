package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.exceptions.ErrorException;
import com.spring.gestiondestock.model.CreditCollecteur;
import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitsCollecter;
import com.spring.gestiondestock.repositories.CustomCreditCollecteurRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomCreditCollecteurRepositoryImpl implements CustomCreditCollecteurRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public CreditCollecteur saveWithReste(CreditCollecteur newCredit) {
        Long idCollecteur = newCredit.getCollecteur().getIdCollecteur();

        List<CreditCollecteur> anciensCredits = entityManager.createQuery(
                        "SELECT c FROM CreditCollecteur c WHERE c.collecteur.idCollecteur = :id ORDER BY c.dateDeCredit DESC,c.idCreditCollecteur DESC",
                        CreditCollecteur.class
                )
                .setParameter("id", idCollecteur)
                .setMaxResults(1)
                .getResultList();

        if (!anciensCredits.isEmpty()) {
            CreditCollecteur dernierCredit = anciensCredits.get(0);
            if (newCredit.getDateDeCredit().isBefore(dernierCredit.getDateDeCredit())) {
                throw new ErrorException("La date du nouveau crédit (" + newCredit.getDateDeCredit() +
                        ") ne peut pas être antérieure à celle du dernier crédit (" + dernierCredit.getDateDeCredit() + ").");
            }
            List<Object[]> rows = entityManager.createNativeQuery("""
                    SELECT pc.quantite, pc.prix_unitaire, pc.unite
                    FROM produits_collecter pc
                    JOIN debit_collecteur d ON pc.id_debit_collecteur = d.id_debit_collecteur
                    WHERE d.id_credit_collecteur = ?
                """)
                    .setParameter(1, dernierCredit.getIdCreditCollecteur())
                    .getResultList();

            double totalDebit = 0;
            for (Object[] row : rows) {
                double quantite = ((Number) row[0]).doubleValue();
                double prix = ((Number) row[1]).doubleValue();
                String unite = (String) row[2];

                if ("T".equals(unite)) {
                    totalDebit += quantite * 1000 * prix;
                } else {
                    totalDebit += quantite * prix;
                }
            }

            double reste = dernierCredit.getMontant() - totalDebit;
            dernierCredit.setStatus(true);
            entityManager.merge(dernierCredit);
            if (reste > 0) {
                newCredit.setMontant(newCredit.getMontant() + reste);
            }
        }

        entityManager.persist(newCredit);
        return newCredit;
    }

}
