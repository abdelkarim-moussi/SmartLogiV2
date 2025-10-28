package com.app.SmartLogiV2.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clients")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type")
public abstract class Client extends Person{

    private String adresse;
    @OneToMany(mappedBy = "client")
    private List<Colis> colisList;

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
