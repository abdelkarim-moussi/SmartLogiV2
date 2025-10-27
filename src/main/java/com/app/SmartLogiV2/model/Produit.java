package com.app.SmartLogiV2.model;

import jakarta.persistence.*;

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
}
