package com.app.api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public ColisProduitKey getId() {
        return id;
    }

    public void setId(ColisProduitKey id) {
        this.id = id;
    }

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }
}
