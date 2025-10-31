package com.app.api.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livreurs")
public class Livreur extends Person{

    private String vehicule;
    @OneToMany(mappedBy = "livreur",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Colis> colisList;
    @ManyToOne
    private Zone zone;

    public Livreur(){}

    public Livreur(String nom, String prenom, String telephone,String email, String vehicule){
        super(nom,prenom,telephone,email);
        this.vehicule = vehicule;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
