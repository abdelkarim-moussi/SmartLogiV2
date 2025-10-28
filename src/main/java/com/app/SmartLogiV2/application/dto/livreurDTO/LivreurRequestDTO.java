package com.app.SmartLogiV2.application.dto.livreurDTO;

public class LivreurRequestDTO {
    private String nom;
    private String preom;
    private String email;
    private String telephone;
    private String vehicule;
    private Long zoneId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPreom() {
        return preom;
    }

    public void setPreom(String preom) {
        this.preom = preom;
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

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}
