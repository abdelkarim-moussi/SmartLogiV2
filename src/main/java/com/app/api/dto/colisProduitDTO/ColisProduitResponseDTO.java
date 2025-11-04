package com.app.api.dto.colisProduitDTO;
import com.app.api.dto.produitDTO.ProduitDTO;
import com.app.api.entity.ColisProduitKey;
import com.app.api.entity.Produit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ColisProduitResponseDTO {
    private ColisProduitKey id;
    private float prix;
    private LocalDate dateAjout;
    private Float poids;
    private int Quantite;
    private ProduitDTO produit;
}
