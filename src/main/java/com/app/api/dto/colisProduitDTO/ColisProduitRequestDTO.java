package com.app.api.dto.colisProduitDTO;
import com.app.api.dto.produitDTO.ProduitDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColisProduitRequestDTO {
    private String nom;
    private Float prix;
    private String categorie;
    private Float poids;
    @Min(1)
    private int quantite;
}
