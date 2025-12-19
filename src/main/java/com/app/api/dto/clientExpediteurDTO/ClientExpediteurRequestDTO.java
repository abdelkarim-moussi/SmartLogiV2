package com.app.api.dto.clientExpediteurDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientExpediteurRequestDTO {
    @NotNull(message = "entrer un nom valide")
    private String nom;
    @NotNull(message = "entrer un prenom valide")
    private String prenom;
    @NotBlank(message = "password est obligatoire")
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @Email(message = "entrer un email valide")
    private String email;
    @NotNull(message = "entrer une adresse valide")
    private String adresse;

    @Size(min = 8,message = "mot de pass doit contenir au moin 8 caractère")
    private String password;

}
