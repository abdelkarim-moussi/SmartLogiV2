package com.app.SmartLogiV2.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ColisProduitKey implements Serializable {

    @Column(name = "produit_id")
    private Long produitId;
    @Column (name = "colis_id")
    private Long colisId;

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getColisId() {
        return colisId;
    }

    public void setColisId(Long colisId) {
        this.colisId = colisId;
    }
}
