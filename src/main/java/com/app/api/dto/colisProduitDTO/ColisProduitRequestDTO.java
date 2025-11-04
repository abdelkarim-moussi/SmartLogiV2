package com.app.api.dto.colisProduitDTO;
import com.app.api.dto.produitDTO.ProduitDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColisProduitRequestDTO {
    @NotNull
    private String produitId;
    private Float prix;
    private String categorie;
    private Float poids;
    @Min(1)
    private int Quantite;
}
