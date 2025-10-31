package com.app.api.entity;

import jakarta.persistence.*;

import java.util.List;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }

    public List<Livreur> getLivreurList() {
        return livreurList;
    }

    public void setLivreurList(List<Livreur> livreurList) {
        this.livreurList = livreurList;
    }
}
