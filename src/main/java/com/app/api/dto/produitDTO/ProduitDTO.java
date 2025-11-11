package com.app.api.dto.produitDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDTO {
    private String id;
    private String nom;
    private Float prix;
    private String categorie;
    private Float poids;
}
