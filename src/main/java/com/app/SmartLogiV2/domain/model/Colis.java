package com.app.SmartLogiV2.domain.model;
import com.app.SmartLogiV2.domain.enums.ColisStatus;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "colis")
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float poids;
    private String description;
    private String adresse;
    private String villeDestination;
    private short priorie;
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
    private Expediteur expediteur;
    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private Destinataire destinataire;
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getPoids() {
        return poids;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public ColisStatus getStatus() {
        return status;
    }

    public void setStatus(ColisStatus status) {
        this.status = status;
    }

    public String getVilleDestination() {
        return villeDestination;
    }

    public void setVilleDestination(String villeDestination) {
        this.villeDestination = villeDestination;
    }

    public Set<ColisProduit> getColisProduits() {
        return colisProduits;
    }

    public void setColisProduits(Set<ColisProduit> colisProduits) {
        this.colisProduits = colisProduits;
    }

    public short getPriorie() {
        return priorie;
    }

    public void setPriorie(short priorie) {
        this.priorie = priorie;
    }

    public void setHistoriqueLivraison(Set<HistoriqueLivraison> historiqueLivraison) {
        this.historiqueLivraison = historiqueLivraison;
    }

    public Set<HistoriqueLivraison> getHistoriqueLivraison() {
        return historiqueLivraison;
    }

    public Expediteur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Expediteur expediteur) {
        this.expediteur = expediteur;
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
