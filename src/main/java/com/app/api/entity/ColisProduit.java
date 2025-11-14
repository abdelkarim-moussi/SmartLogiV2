package com.app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "colis_produits")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColisProduit {

    @EmbeddedId
    private ColisProduitKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("colisId")
    @JoinColumn(name = "colis_id")
    private Colis colis;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private int quantite;

    private float prix;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    @PrePersist
    public void prePersist() {
        if (dateAjout == null) {
            dateAjout = LocalDate.now();
        }
        if (id == null) {
            id = new ColisProduitKey();
        }
    }
}