package com.app.api.dto.colisDTO;

import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.persistence.*;

import java.util.Set;

public class ColisRequestDTO {

    private String id;
    private Float poids;
    private String description;
    private String adresse;
    private String villeDestination;
    private ColisPriority priority;
    private ColisStatus status;
    private String livreurId;
    private Set<ColisProduit> colisProduits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    private Expediteur expediteurId;
    private Destinataire destinataireId;
    private Zone zoneId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Set<ColisProduit> getColisProduits() {
        return colisProduits;
    }

    public void setColisProduits(Set<ColisProduit> colisProduits) {
        this.colisProduits = colisProduits;
    }

    public Set<HistoriqueLivraison> getHistoriqueLivraison() {
        return historiqueLivraison;
    }

    public void setHistoriqueLivraison(Set<HistoriqueLivraison> historiqueLivraison) {
        this.historiqueLivraison = historiqueLivraison;
    }

    public Expediteur getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(Expediteur expediteurId) {
        this.expediteurId = expediteurId;
    }

    public Destinataire getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Destinataire destinataireId) {
        this.destinataireId = destinataireId;
    }

    public Zone getZoneId() {
        return zoneId;
    }

    public void setZoneId(Zone zoneId) {
        this.zoneId = zoneId;
    }
}
