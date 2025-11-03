package com.app.api.dto.produitDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProduitResponseDTO {
    private String id;
    private Float prix;
    private String nom;
    private String categorie;
    private Float poids;
}
