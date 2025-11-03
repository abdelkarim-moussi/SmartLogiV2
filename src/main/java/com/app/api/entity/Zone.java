package com.app.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "zones")
public class Zone{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String codePostal;
    @OneToMany(mappedBy = "zone")
    private List<Colis> colisList;
    @OneToMany(mappedBy = "zone")
    private List<Livreur> livreurList;

}
