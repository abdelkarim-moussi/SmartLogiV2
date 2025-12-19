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
    @NotBlank(message = "password est obligatoire")
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @Size(min = 8,message = "mot de pass doit contenir au moin 8 caractère")
    private String email;
    @NotBlank(message = "entre une vehicule")
    private String vehicule;
    private Long zoneId;

    @NotBlank(message = "password is required")
    private String nonUtilisateur;
    @NotBlank(message = "user name is required")
    private String password;
}
