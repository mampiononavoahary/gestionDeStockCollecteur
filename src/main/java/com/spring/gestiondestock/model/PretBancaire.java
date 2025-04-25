
package com.spring.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.gestiondestock.model.enums.Unite;
import com.spring.gestiondestock.model.extractModel.CustomLocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pret_bancaire")
@Data
public class PretBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pret_bancaire")
    private Long idPretBancaire;

    @Column(name = "date_de_pret")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateDePret;

    @Column(name = "produit")
    private Integer produit;

    @Column(name = "quantite")
    private Double quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite")
    private Unite unite;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "taux_augmentation", precision = 5, scale = 3)
    private BigDecimal tauxAugmentation;

    @Column(name = "taux_mensuel", precision = 5, scale = 3)
    private BigDecimal tauxMensuel;

    @Column(name = "date_de_remboursement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateDeRemboursement;

}
