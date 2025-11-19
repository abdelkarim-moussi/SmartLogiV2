package com.app.api.dto.livreurDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivreurRequestDTO {
    @NotBlank(message = "entrer un nom")
    private String nom;
    @NotBlank(message = "enter un prenom")
    private String prenom;
    @Email(message = "entrer un email valide")
    private String email;
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @NotBlank(message = "entre une vehicule")
    private String vehicule;
    private Long zoneId;
}
