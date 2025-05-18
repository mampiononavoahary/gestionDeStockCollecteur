package com.spring.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Builder
@ToString
@Entity
@Table(name = "credit_collecteur")
public class CreditCollecteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credit_collecteur")
    private Long idCreditCollecteur;

    @Column(name = "date_de_credit")
    private LocalDateTime dateDeCredit;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "description")
    private String description;

    @Column(name = "referance_credit", length = 200, unique = true)
    private String referanceCredit;

    @Column(name = "status")
    private Boolean status = false;

    @ManyToOne
    @JoinColumn(name = "id_collecteur")
    @JsonProperty("id_collecteur")
    private Collecteur collecteur;

    @PrePersist
    public void prePersist() {
        if (referanceCredit == null || referanceCredit.isEmpty()) {
            String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // e.g. 20250423
            int randomPart = new Random().nextInt(10000); // 0 to 9999
            this.referanceCredit = String.format("CR-%s-%04d", datePart, randomPart); // CR-20250423-0482
        }
    }

}
