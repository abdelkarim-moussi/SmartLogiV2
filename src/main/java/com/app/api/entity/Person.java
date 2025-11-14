package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36, columnDefinition = "VARCHAR(36)")
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    @Column(nullable = true)
    private String email;

    public Person(){}
    public Person(String nom,String prenom, String telephone, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }

}
