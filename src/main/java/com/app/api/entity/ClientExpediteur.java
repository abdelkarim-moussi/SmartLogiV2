package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients_expediteurs")
public class ClientExpediteur extends Person{

    private String adresse;
    public ClientExpediteur(){}
    public ClientExpediteur(String nom, String prenom, String telephone, String email,String adresse){
        super(nom,prenom,telephone,email);
        this.adresse = adresse;
    }

    @OneToMany(mappedBy = "clientExpediteur",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Colis> colisEnvoyes;

}
