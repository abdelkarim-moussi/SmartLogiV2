package com.app.api.dto.colisDTO;

import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ColisRequestDTO {
    @Min(value = 1, message = "poid doit être supereieur à 1g")
    private Float poids;
    private String description;
    @NotBlank(message = "entrer une adresse valide")
    private String adresse;
    @NotBlank(message = "entrer ville de destination valide")
    private String villeDestination;
    @NotBlank(message = "definir la prioritie de la colis (standard,express)")
    private ColisPriority priority;
    @NotBlank(message = "definir le status (créer,colécté,en_stock,en_transite,livré)")
    private ColisStatus status;
    @NotBlank(message = "choisir un livreur valide")
    private String livreurId;
    @NotEmpty(message = "colis doit avoir au minimum un produit")
    private Set<ColisProduit> colisProduits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    @NotBlank(message = "entrer l'expediteur")
    private String expediteurId;
    @NotBlank(message = "entrer le destinataire")
    private String destinataireId;
    @NotBlank(message = "definir la zone ou la coli doit s'envoyé")
    private String zoneId;

}
