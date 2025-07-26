package com.spring.gestiondestock.repositories.impl;

import com.spring.gestiondestock.db.Connect;
import com.spring.gestiondestock.model.CreditCollecteur;
import com.spring.gestiondestock.model.DebitCollecteur;
import com.spring.gestiondestock.model.ProduitAvecDetail;
import com.spring.gestiondestock.model.ProduitsCollecter;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.repositories.InterfaceCreditCollecteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduitsCollecterRepositoriesImpl {
    private static Connection connection;

    private final InterfaceCreditCollecteur interfaceCreditCollecteur;

    @PersistenceContext
    private EntityManager entityManager;

    public ProduitsCollecterRepositoriesImpl(InterfaceCreditCollecteur interfaceCreditCollecteur) {
        this.interfaceCreditCollecteur = interfaceCreditCollecteur;
    }

    public static void getConnection() throws SQLException, ClassNotFoundException {
        Connect connect = new Connect();
        connection = connect.CreateConnection();
    }
    private ProduitsCollecter extractProduitsCollecter(ResultSet resultSet) throws SQLException {
        ProduitsCollecter produitsCollecter = new ProduitsCollecter();
        produitsCollecter.setIdProduitCollecter(resultSet.getLong("id_produit_collecter"));
        produitsCollecter.setDebitCollecteur(DebitCollecteur.builder().idDebitCollecteur(resultSet.getLong("id_debit_collecteur")).build());
        produitsCollecter.setProduitAvecDetail(ProduitAvecDetail.builder().id_produit_avec_detail(resultSet.getInt("id_produit_avec_detail")).build().getId_produit_avec_detail());
        produitsCollecter.setQuantite(resultSet.getDouble("quantite"));
        produitsCollecter.setUnite(Unite.valueOf(resultSet.getString("unite")));
        produitsCollecter.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
        produitsCollecter.setLieu_stock(LieuDeTransaction.valueOf(resultSet.getString("lieu_stock")));
        return produitsCollecter;
    }
    public List<ProduitsCollecter> getAllProduitsCollecter() throws SQLException, ClassNotFoundException {
        getConnection();
        String query = "SELECT * FROM produits_collecter";
        List<ProduitsCollecter> produitsCollecters = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProduitsCollecter produitsCollecter = extractProduitsCollecter(resultSet);
                produitsCollecters.add(produitsCollecter);
            }
            return produitsCollecters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ProduitsCollecter createProduitsCollecter(ProduitsCollecter produitsCollecter) throws SQLException, ClassNotFoundException {
        getConnection();
        String query = "INSERT INTO produits_collecter (id_debit_collecteur, id_produit_avec_detail, quantite, unite, prix_unitaire, lieu_stock) VALUES (?, ?, ?, CAST(? AS unite), ?, CAST(? AS lieu_de_transaction))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, produitsCollecter.getDebitCollecteur().getIdDebitCollecteur());
            preparedStatement.setInt(2, produitsCollecter.getProduitAvecDetail());
            preparedStatement.setDouble(3, produitsCollecter.getQuantite());
            preparedStatement.setString(4, produitsCollecter.getUnite().toString());
            preparedStatement.setDouble(5, produitsCollecter.getPrix_unitaire());
            preparedStatement.setString(6, produitsCollecter.getLieu_stock().toString());
            preparedStatement.executeUpdate();
            return produitsCollecter;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private double calculMontant(double quantite, double prix, Unite unite) {
        return "T".equals(unite.name()) ? quantite * 1000 * prix : quantite * prix;
    }

    @Transactional
    public void updateProduitsCollecterEtMettreAJourCredit(Double nouvelleQuantite, Unite nouvelleUnite, Double nouveauPrixUnitaire, int idProduitCollecter) {
        ProduitsCollecter ancien = entityManager.find(ProduitsCollecter.class, idProduitCollecter);
        if (ancien == null) {
            throw new RuntimeException("Produit collecté introuvable avec ID: " + idProduitCollecter);
        }

        double ancienMontant = calculMontant(ancien.getQuantite(), ancien.getPrix_unitaire(), ancien.getUnite());
        double nouveauMontant = calculMontant(nouvelleQuantite, nouveauPrixUnitaire, nouvelleUnite);
        double delta = nouveauMontant - ancienMontant;

        ancien.setQuantite(nouvelleQuantite);
        ancien.setUnite(nouvelleUnite);
        ancien.setPrix_unitaire(nouveauPrixUnitaire);
        entityManager.merge(ancien);

        DebitCollecteur debit = ancien.getDebitCollecteur();
        if (debit == null || debit.getCreditCollecteur() == null) {
            throw new RuntimeException("Débit ou crédit associé introuvable pour ce produit collecté.");
        }

        CreditCollecteur creditAssocie = debit.getCreditCollecteur();
        Long idCollecteur = creditAssocie.getCollecteur().getIdCollecteur();

        long nombreCreditsActifs = entityManager.createQuery("""
        SELECT COUNT(c) FROM CreditCollecteur c
        WHERE c.collecteur.idCollecteur = :idCollecteur
    """, Long.class)
                .setParameter("idCollecteur", idCollecteur)
                .getSingleResult();

        if (nombreCreditsActifs <= 1) {
            System.out.println("Un seul crédit actif : aucun ajustement du montant nécessaire.");
            return;
        }

        CreditCollecteur dernierActif = entityManager.createQuery("""
        SELECT c FROM CreditCollecteur c
        WHERE c.collecteur.idCollecteur = :idCollecteur AND c.status = false
        ORDER BY c.dateDeCredit DESC
    """, CreditCollecteur.class)
                .setParameter("idCollecteur", idCollecteur)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun crédit actif trouvé pour ce collecteur."));

        double nouveauMontantCredit = dernierActif.getRecentreste() - delta;
        if (nouveauMontantCredit < 0) {
            throw new RuntimeException("Le crédit ne peut pas être négatif après la mise à jour.");
        }

        dernierActif.setRecentreste(nouveauMontantCredit);
        entityManager.merge(dernierActif);
    }


}
