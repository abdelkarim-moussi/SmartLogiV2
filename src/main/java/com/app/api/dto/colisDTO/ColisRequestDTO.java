package com.app.api.dto.colisDTO;

import com.app.api.dto.colisProduitDTO.ColisProduitRequestDTO;
import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColisRequestDTO {
    @NotNull
    @Min(value = 1, message = "poid doit être supereieur à 1g")
    private Float poids;
    @NotNull
    private String description;
    @NotNull
    @NotBlank(message = "entrer ville de destination valide")
    private String villeDestination;
    @NotNull(message = "definir la prioritie de la colis (standard,express)")
    private ColisPriority priority;
    @NotNull(message = "definir le status (créer,colécté,en_stock,en_transite,livré)")
    private ColisStatus status;
    @NotNull
//    @NotEmpty(message = "colis doit avoir au minimum un produit")
    private Set<ColisProduitRequestDTO> produits;
    @NotNull(message = "entrer l'expediteur")
    private String clientExpediteurId;
    @NotNull(message = "entrer le destinataire")
    private DestinataireRequestDTO destinataire;
    @NotNull
    @NotBlank(message = "entrer le code postal")
    private String codePostal;

}
