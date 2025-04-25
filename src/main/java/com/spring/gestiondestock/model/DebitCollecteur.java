package com.spring.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Builder
@ToString
@Entity
@Table(name = "debit_collecteur")
public class DebitCollecteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_debit_collecteur")
    private Long idDebitCollecteur;

    @Column(name = "date_de_debit")
    private LocalDateTime dateDeDebit;

    @Column(name = "lieu_de_collection")
    private String lieuDeCollection;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_credit_collecteur")
    private CreditCollecteur creditCollecteur;

}
