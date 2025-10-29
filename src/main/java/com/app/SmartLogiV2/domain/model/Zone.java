package com.app.SmartLogiV2.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "zones")
public class Zone{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codePostal;
    @OneToMany(mappedBy = "zone")
    private List<Colis> colisList;
    @OneToMany(mappedBy = "zone")
    private List<Livreur> livreurList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
