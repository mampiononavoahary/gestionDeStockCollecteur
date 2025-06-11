package com.spring.gestiondestock.service;

import com.spring.gestiondestock.dtos.requests.ProduitsCollecterRequest;
import com.spring.gestiondestock.dtos.responses.ProduitsCollecterResponse;
import com.spring.gestiondestock.exceptions.ErrorException;
import com.spring.gestiondestock.mappers.ProduitsCollecterMapper;
import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.Stock;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.repositories.InterfaceProduitsCollecter;
import com.spring.gestiondestock.repositories.impl.ProduitAvecDetailRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.ProduitsCollecterRepositoriesImpl;
import com.spring.gestiondestock.repositories.impl.StockRepositoriesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitsCollecterServiceImpl {
    private final ProduitsCollecterRepositoriesImpl produitsCollecterRepositories;
    private final DebitCollecteurService debitCollecteurService;
    private final StockRepositoriesImpl stockRepositories;
    private final ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories;
    private final ProduitsCollecterMapper produitsCollecterMapper;
    private final InterfaceProduitsCollecter interfaceProduitsCollecter;

    @Autowired
    public ProduitsCollecterServiceImpl(ProduitsCollecterRepositoriesImpl produitsCollecterRepositories, DebitCollecteurService debitCollecteurService, StockRepositoriesImpl stockRepositories, ProduitAvecDetailRepositoriesImpl produitAvecDetailRepositories, ProduitsCollecterMapper produitsCollecterMapper, InterfaceProduitsCollecter interfaceProduitsCollecter) {
        this.produitsCollecterRepositories = produitsCollecterRepositories;
        this.debitCollecteurService = debitCollecteurService;
        this.stockRepositories = stockRepositories;
        this.produitAvecDetailRepositories = produitAvecDetailRepositories;
        this.produitsCollecterMapper = produitsCollecterMapper;
        this.interfaceProduitsCollecter = interfaceProduitsCollecter;
    }

    public List<ProduitsCollecterResponse> saveProduitsCollecter(List<ProduitsCollecterRequest> produitsCollecterRequests, DebitCollecteur debitCollecteur) throws SQLException, ClassNotFoundException {
        DebitCollecteur debitCollecteur1 = new DebitCollecteur();
        debitCollecteur1.setDateDeDebit(debitCollecteur.getDateDeDebit());
        debitCollecteur1.setLieuDeCollection(debitCollecteur.getLieuDeCollection());
        debitCollecteur1.setDepense(debitCollecteur.getDepense());
        debitCollecteur1.setDescription(debitCollecteur.getDescription());
        debitCollecteur1.setCreditCollecteur(debitCollecteur.getCreditCollecteur());

        var debitCollecteurSaved = debitCollecteurService.saveDebitCollecteur(debitCollecteur1);

        List<ProduitsCollecterResponse> produitsCollecterResponses = new ArrayList<>();
        for (ProduitsCollecterRequest produitsCollecterRequest : produitsCollecterRequests) {
            produitsCollecterRequest.setId_debit_collecter(debitCollecteurSaved.getIdDebitCollecteur());

            Stock stock = stockRepositories.findByLieuAndProduit(
                    produitsCollecterRequest.getLieu_stock(),
                    produitsCollecterRequest.getId_produit_avec_detail()
            );

            if (stock == null) {
                stock = new Stock();
                stock.setLieu_de_transaction(LieuDeTransaction.valueOf(produitsCollecterRequest.getLieu_stock()));
                stock.setProduitAvecDetail(ProduitAvecDetail.builder()
                        .id_produit_avec_detail(produitsCollecterRequest.getId_produit_avec_detail())
                        .build());

                if ("T".equals(produitsCollecterRequest.getUnite())) {
                    stock.setQuantite_stock(produitsCollecterRequest.getQuantite() * 1000);
                } else if ("KG".equals(produitsCollecterRequest.getUnite())) {
                    stock.setQuantite_stock(produitsCollecterRequest.getQuantite());
                } else {
                    throw new IllegalArgumentException("Unité invalide : " + produitsCollecterRequest.getUnite());
                }
                stock.setUnite(Unite.valueOf("KG"));

                stockRepositories.saveStock(stock);
            } else {
                if (!stock.getLieu_de_transaction().name().equals(produitsCollecterRequest.getLieu_stock())) {
                    throw new ErrorException("Le lieu de stock spécifié est introuvable.");
                }

                if ("T".equals(produitsCollecterRequest.getUnite())) {
                    stock.setQuantite_stock(stock.getQuantite_stock() + (produitsCollecterRequest.getQuantite() * 1000));
                } else if ("KG".equals(produitsCollecterRequest.getUnite())) {
                    stock.setQuantite_stock(stock.getQuantite_stock() + produitsCollecterRequest.getQuantite());
                } else {
                    throw new IllegalArgumentException("Unité invalide : " + produitsCollecterRequest.getUnite());
                }

                stockRepositories.updateStock(stock, stock.getId_stock());
            }

            var toEntity = produitsCollecterMapper.toEntity(produitsCollecterRequest);
            var save = produitsCollecterRepositories.createProduitsCollecter(toEntity);

            produitsCollecterResponses.add(produitsCollecterMapper.toResponse(save));
        }
        return produitsCollecterResponses;
    }

    public void updateProduitsCollecter(Double quantite, Unite unite, Double prix_unitaire, int id_produit_collecter) throws SQLException, ClassNotFoundException {
        produitsCollecterRepositories.updateProduitsCollecterEtMettreAJourCredit(quantite, unite, prix_unitaire, id_produit_collecter);
    }

    public void deleteProduitsCollecter(Long id){
        interfaceProduitsCollecter.deleteById(id);
    }
}
