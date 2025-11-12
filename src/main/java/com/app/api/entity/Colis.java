package com.app.api.entity;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "colis")
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;
    @OneToMany(mappedBy = "colis")
    private Set<ColisProduit> colisProduits;
    @OneToMany(mappedBy = "colis")
    private Set<HistoriqueLivraison> historiqueLivraison;
    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private ClientExpediteur clientExpediteur;
    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private Destinataire destinataire;
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

}
