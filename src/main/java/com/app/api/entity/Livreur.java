package com.app.api.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "livreurs")
public class Livreur extends Person{

    private String vehicule;
    @OneToMany(mappedBy = "livreur",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Colis> colisLivrer;
    @ManyToOne
    private Zone zone;

    public Livreur(){}

    public Livreur(String nom, String prenom, String telephone,String email, String vehicule){
        super(nom,prenom,telephone,email);
        this.vehicule = vehicule;
    }
}
