package com.app.SmartLogiV2.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float prix;
    private String nom;
    private String categorie;
    private Float poids;

    @OneToMany(mappedBy = "produits")
    private Set<ColisProduit> colisProduits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Float getPoids() {
        return poids;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public Set<ColisProduit> getColisProduits() {
        return colisProduits;
    }

    public void setColisProduits(Set<ColisProduit> colisProduits) {
        this.colisProduits = colisProduits;
    }
}
