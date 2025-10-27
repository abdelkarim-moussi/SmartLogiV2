package com.app.SmartLogiV2.model;
import com.app.SmartLogiV2.enums.ColisStatus;
import jakarta.persistence.*;

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
    @Enumerated(EnumType.STRING)
    private ColisStatus status;
    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

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
}
