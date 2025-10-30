package com.app.api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Destinataire extends Client{

    @OneToMany(mappedBy = "destinataire",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Colis> colisList;

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }
}
