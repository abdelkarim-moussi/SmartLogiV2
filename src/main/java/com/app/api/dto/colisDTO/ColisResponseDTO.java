package com.app.api.dto.colisDTO;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.dto.colisProduitDTO.ColisProduitResponseDTO;
import com.app.api.dto.destinataireDTO.DestinataireResponseDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.dto.zoneDTO.ZoneResponseDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColisResponseDTO {
    private String id;
    private Float poids;
    private String description;
    private String villeDestination;
    private ColisPriority priority;
    private ColisStatus status;
    private LivreurResponseDTO livreur;
    private Set<ColisProduitResponseDTO> produits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    private ClientExpediteurResponseDTO clientExpediteur;
    private DestinataireResponseDTO destinataire;
    private ZoneResponseDTO zone;

}
