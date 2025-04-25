package com.spring.gestiondestock.service;

import com.spring.gestiondestock.model.PretBancaire;
import com.spring.gestiondestock.repositories.PretBancaireRepository;
import jakarta.persistence.JoinColumn;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PretBancaireService {

    private final PretBancaireRepository pretBancaireRepository;

    public PretBancaireService(PretBancaireRepository pretBancaireRepository) {
        this.pretBancaireRepository = pretBancaireRepository;
    }

    public PretBancaire savePretBancaire(PretBancaire pretBancaire) {
        return pretBancaireRepository.save(pretBancaire);
    }

    public List<PretBancaire> getPretBancaires() {
        return pretBancaireRepository.findAll();
    }

    public double calculateFinalAmount(Long id) {
        PretBancaire loan = pretBancaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prêt bancaire non trouvé pour l'id : " + id));

        double montantInitial = loan.getQuantite() * loan.getPrixUnitaire();

        double montantAjuste = montantInitial * (1 + loan.getTauxAugmentation().doubleValue());

        long nombreMois = ChronoUnit.MONTHS.between(loan.getDateDePret(), loan.getDateDeRemboursement());

        double montantFinal = montantAjuste * Math.pow(1 + loan.getTauxMensuel().doubleValue(), nombreMois);

        return montantFinal;
    }
}