package com.app.api.dto.colisProduitDTO;
import com.app.api.dto.produitDTO.ProduitDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ColisProduitResponseDTO {
    private float prix;
    private Float poids;
    private int quantite;
    private LocalDate dateAjout;
    private ProduitDTO produit;
}
