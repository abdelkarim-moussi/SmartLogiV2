package com.app.api.dto.colisDTO;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.dto.destinataireDTO.DestinataireResponseDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.dto.zoneDTO.ZoneResponseDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ColisResponseDTO {
    private String id;
    private Float poids;
    private String description;
    private String adresse;
    private String villeDestination;
    private ColisPriority priority;
    private ColisStatus status;
    private LivreurResponseDTO livreur;
    private Set<ColisProduit> colisProduits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    private ClientExpediteurResponseDTO clientExpediteur;
    private DestinataireResponseDTO destinataire;
    private ZoneResponseDTO zone;

}
