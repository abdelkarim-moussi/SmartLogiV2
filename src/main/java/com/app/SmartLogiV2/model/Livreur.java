package com.app.SmartLogiV2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livreurs")
public class Livreur {

    @Id
    private String id = generateId();
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String vehicule;
    @Column(unique = true)
    private String telephone;
    @OneToMany(mappedBy = "livreur",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Colis> colisList;

    public Livreur(){}

    public Livreur(String nom, String prenom, String telephone, String vehicule){
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.vehicule = vehicule;
    }

    public String generateId(){
        return UUID.randomUUID().toString().replace("-","").substring(0,10);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", vehicule='" + vehicule + '\'' +
                ", telephone='" + telephone + '\'' +
                ", colisList=" + colisList +
                '}';
    }
}
