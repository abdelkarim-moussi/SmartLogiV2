package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "destinataires")
public class Destinataire extends Person{

    private String adresse;

    public Destinataire(){}
    public Destinataire(String nom, String prenom, String telephone, String email,String adresse){
        super(nom,prenom,telephone,email);
        this.adresse = adresse;
    }

    @OneToMany(mappedBy = "destinataire",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Colis> colisRecus;

}
