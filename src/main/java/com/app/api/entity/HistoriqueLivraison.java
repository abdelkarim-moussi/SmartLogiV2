package com.app.api.entity;

import com.app.api.enums.ColisStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@Entity
@Table(name = "historiques_livraison")
public class HistoriqueLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private ColisStatus colisStatus;
    private LocalDate dateChangement;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "colis_id")
    private Colis colis;

}
