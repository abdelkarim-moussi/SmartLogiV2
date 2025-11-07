package com.app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "zones")
@NoArgsConstructor
@AllArgsConstructor
public class Zone{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String codePostal;
    @OneToMany(mappedBy = "zone")
    private List<Colis> colisList;
    @OneToMany(mappedBy = "zone")
    private List<Livreur> livreurList;

    public Zone(String codePostal){
        this.codePostal = codePostal;
    }

}
