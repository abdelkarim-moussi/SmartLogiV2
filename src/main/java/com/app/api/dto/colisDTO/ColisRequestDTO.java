package com.app.api.dto.colisDTO;

import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

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
//    @NotNull
//    @NotEmpty(message = "colis doit avoir au minimum un produit")
    private List<ColisProduit> colisProduits;
    @NotNull
    @NotBlank(message = "entrer l'expediteur")
    private String expediteurId;
    @NotNull
    @NotBlank(message = "entrer le destinataire")
    private String destinataireId;
    @NotNull
    @NotBlank(message = "definir la zone ou la coli doit s'envoyé")
    private String zoneId;

    public Float getPoids() {
        return poids;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVilleDestination() {
        return villeDestination;
    }

    public void setVilleDestination(String villeDestination) {
        this.villeDestination = villeDestination;
    }

    public ColisPriority getPriority() {
        return priority;
    }

    public void setPriority(ColisPriority priority) {
        this.priority = priority;
    }

    public ColisStatus getStatus() {
        return status;
    }

    public void setStatus(ColisStatus status) {
        this.status = status;
    }

    public String getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(String livreurId) {
        this.livreurId = livreurId;
    }

    public List<ColisProduit> getColisProduits() {
        return colisProduits;
    }

    public void setColisProduits(List<ColisProduit> colisProduits) {
        this.colisProduits = colisProduits;
    }

    public String getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(String expediteurId) {
        this.expediteurId = expediteurId;
    }

    public String getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(String destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
