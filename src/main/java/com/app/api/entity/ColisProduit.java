package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "colis_produits")
public class ColisProduit {
    @EmbeddedId
    ColisProduitKey id;
    @ManyToOne
    @MapsId("colisId")
    @JoinColumn(name = "colis_id")
    private Colis colis;
    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;
    private int Quantite;
    private float prix;
    private LocalDate dateAjout;

}
