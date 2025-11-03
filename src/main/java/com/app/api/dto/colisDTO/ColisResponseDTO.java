package com.app.api.dto.colisDTO;

import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import java.util.Set;

public class ColisResponseDTO {
    private String id;
    private Float poids;
    private String description;
    private String adresse;
    private String villeDestination;
    private ColisPriority priority;
    private ColisStatus status;
    private Livreur livreur;
    private Set<ColisProduit> colisProduits;
    private Set<HistoriqueLivraison> historiqueLivraison;
    private ClientExpediteur clientExpediteur;
    private Destinataire destinataire;
    private Zone zone;

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

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
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

    public ClientExpediteur getClientExpediteur() {
        return clientExpediteur;
    }

    public void setClientExpediteur(ClientExpediteur clientExpediteur) {
        this.clientExpediteur = clientExpediteur;
    }

    public Destinataire getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Destinataire destinataire) {
        this.destinataire = destinataire;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
