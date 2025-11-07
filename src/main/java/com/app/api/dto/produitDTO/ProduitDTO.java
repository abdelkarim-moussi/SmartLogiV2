package com.app.api.dto.produitDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProduitDTO {
    private String id;
    private String nom;
    private Float prix;
    private String categorie;
    private Float poids;
}
