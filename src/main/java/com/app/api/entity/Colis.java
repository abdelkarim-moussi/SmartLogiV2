package com.app.api.entity;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "colis")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Float poids;
    private String description;
    private String villeDestination;

    @Enumerated(EnumType.STRING)
    private ColisPriority priority;

    @Enumerated(EnumType.STRING)
    private ColisStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id")
    private ClientExpediteur clientExpediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id")
    private Destinataire destinataire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @OneToMany(mappedBy = "colis", cascade = CascadeType.ALL)
    private Set<ColisProduit> colisProduits = new HashSet<>();

    @OneToMany(mappedBy = "colis", cascade = CascadeType.ALL)
    private Set<HistoriqueLivraison> historiqueLivraison = new HashSet<>();

    // Helper methods
    public void addColisProduit(ColisProduit colisProduit) {
        colisProduits.add(colisProduit);
        colisProduit.setColis(this);
    }

    public void addHistorique(HistoriqueLivraison historique) {
        historiqueLivraison.add(historique);
        historique.setColis(this);
    }
}