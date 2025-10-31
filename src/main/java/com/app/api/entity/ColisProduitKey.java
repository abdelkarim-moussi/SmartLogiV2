package com.app.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ColisProduitKey implements Serializable {

    @Column(name = "produit_id")
    private String produitId;
    @Column (name = "colis_id")
    private String colisId;

    public String getProduitId() {
        return produitId;
    }

    public void setProduitId(String produitId) {
        this.produitId = produitId;
    }

    public String getColisId() {
        return colisId;
    }

    public void setColisId(String colisId) {
        this.colisId = colisId;
    }
}
