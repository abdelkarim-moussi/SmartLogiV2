package com.app.api.dto.colisDTO;

import com.app.api.dto.colisProduitDTO.ColisProduitRequestDTO;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ColisRequestDTO {
    @NotNull
    @Min(value = 1, message = "poid doit être supereieur à 1g")
    private Float poids;
    @NotNull
    private String description;
    @NotNull
    @NotBlank(message = "entrer une adresse valide")
    private String adresse;
    @NotNull
    @NotBlank(message = "entrer ville de destination valide")
    private String villeDestination;
    @NotNull(message = "definir la prioritie de la colis (standard,express)")
    private ColisPriority priority;
    @NotNull
    @NotNull(message = "definir le status (créer,colécté,en_stock,en_transite,livré)")
    private ColisStatus status;
    @NotNull
    @NotBlank(message = "choisir un livreur valide")
    private String livreurId;
    @NotNull
    @NotEmpty(message = "colis doit avoir au minimum un produit")
    private Set<ColisProduitRequestDTO> produits;
    @NotNull
    @NotBlank(message = "entrer l'expediteur")
    private String clientExpediteurId;
    @NotNull
    @NotBlank(message = "entrer le destinataire")
    private String destinataireId;
    @NotNull
    @NotBlank(message = "definir la zone ou la coli doit s'envoyé")
    private String zoneId;

}
