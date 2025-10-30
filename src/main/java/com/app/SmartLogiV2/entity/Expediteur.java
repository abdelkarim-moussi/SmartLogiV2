package com.app.SmartLogiV2.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("expediteur")
public class Expediteur extends Client{

    @OneToMany(mappedBy = "expediteur")
    private List<Colis> colisList;

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }
}
