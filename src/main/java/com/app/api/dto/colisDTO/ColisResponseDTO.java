package com.app.api.dto.colisDTO;

import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class ColisResponseDTO {
    private Float poids;
    private String description;
    private String adresse;
    private String villeDestination;
    private ColisPriority priority;
    private ColisStatus status;
    private Livreur livreur;
    private Set<ColisProduit> colisProduits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    private Expediteur expediteur;
    private Destinataire destinataire;
    private Zone zone;

}
