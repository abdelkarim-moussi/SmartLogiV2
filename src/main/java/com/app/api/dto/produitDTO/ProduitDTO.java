package com.app.api.dto.produitDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProduitDTO {
    private String id;
    private String nom;
    private Float prix;
    private String categorie;
    private Float poids;
}
