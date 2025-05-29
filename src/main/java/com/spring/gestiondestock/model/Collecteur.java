package com.spring.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.gestiondestock.model.enums.CategoryClient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@Data
@ToString
@Entity
@Table(name = "collecteur")
public class Collecteur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_collecteur")
    private Long idCollecteur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private CategoryClient categorie;

    @OneToMany(mappedBy = "collecteur", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<CreditCollecteur> creditCollecteurs;
}
