package com.app.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public abstract class Client extends Person{

    private String adresse;

    public Client(){}
    public Client(String nom, String prenom, String telephone, String email,String adresse){
        super(nom,prenom,telephone,email);
        this.adresse = adresse;
    }
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
