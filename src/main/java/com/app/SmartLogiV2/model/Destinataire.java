package com.app.SmartLogiV2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("destinataire")
public class Destinataire extends Client{

    @OneToMany(mappedBy = "destinataire")
    private List<Colis> colisList;

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }
}
