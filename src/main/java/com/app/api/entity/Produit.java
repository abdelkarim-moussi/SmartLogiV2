package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Float prix;
    private String nom;
    private String categorie;
    private Float poids;

    @OneToMany(mappedBy = "produit")
    private Set<ColisProduit> colisProduits;
}
