package com.spring.gestiondestock.repositories.impl;

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

        // 1. Récupère le dernier crédit
        List<CreditCollecteur> anciensCredits = entityManager.createQuery(
                        "SELECT c FROM CreditCollecteur c WHERE c.collecteur.idCollecteur = :id ORDER BY c.dateDeCredit DESC",
                        CreditCollecteur.class
                )
                .setParameter("id", idCollecteur)
                .setMaxResults(1)
                .getResultList();

        if (!anciensCredits.isEmpty()) {
            CreditCollecteur dernierCredit = anciensCredits.get(0);

            // 2. Récupère tous les produits liés à ce crédit via les débits
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

            // 4. Calcule le reste
            double reste = dernierCredit.getMontant() - totalDebit;
            //5. Update status of last credit
            dernierCredit.setStatus(true);
            entityManager.merge(dernierCredit);
            if (reste > 0) {
                newCredit.setMontant(newCredit.getMontant() + reste);
            }
        }

        // 5. Persist le nouveau crédit
        entityManager.persist(newCredit);
        return newCredit;
    }

}
