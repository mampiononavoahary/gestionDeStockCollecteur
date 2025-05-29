package com.spring.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.gestiondestock.model.enums.LieuDeTransaction;
import com.spring.gestiondestock.model.enums.Unite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Builder
@Entity
@Table(name = "produits_collecter")
public class ProduitsCollecter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produits_collecter")
    private Long idProduitCollecter;

    @ManyToOne
    @JoinColumn(name = "id_debit_collecteur")
    @JsonProperty("id_debit_collecteur")
    @JsonIgnore
    private DebitCollecteur debitCollecteur;

    @Column(name = "id_produit_avec_detail")
    private int produitAvecDetail;

    @Column(name = "quantite")
    private Double quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite")
    private Unite unite;

    @Column(name = "prix_unitaire")
    private Double prix_unitaire;

    @Enumerated(EnumType.STRING)
    @Column(name = "lieu_de_transaction")
    private LieuDeTransaction lieu_stock;
}
