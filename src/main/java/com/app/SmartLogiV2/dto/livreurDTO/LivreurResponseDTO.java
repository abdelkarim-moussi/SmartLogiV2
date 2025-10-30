package com.app.SmartLogiV2.dto.livreurDTO;

import com.app.SmartLogiV2.entity.Zone;

public class LivreurResponseDTO {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String vehicule;
    private Zone zone;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
