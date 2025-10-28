package com.app.SmartLogiV2.domain.model;

import com.app.SmartLogiV2.domain.enums.ColisStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "historiques_livraison")
public class HistoriqueLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private ColisStatus colisStatus;
    private LocalDate dateChangement;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "colis_id")
    private Colis colis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ColisStatus getColisStatus() {
        return colisStatus;
    }

    public void setColisStatus(ColisStatus colisStatus) {
        this.colisStatus = colisStatus;
    }

    public LocalDate getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDate dateChangement) {
        this.dateChangement = dateChangement;
    }

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
