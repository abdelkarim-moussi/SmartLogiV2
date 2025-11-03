package com.app.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ColisProduitKey implements Serializable {

    @Column(name = "produit_id")
    private String produitId;
    @Column (name = "colis_id")
    private String colisId;

}
