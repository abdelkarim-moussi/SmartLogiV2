package com.app.api.dto.destinataireDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DestinataireRequestDTO {

    @NotNull(message = "entrer un nom valide")
    private String nom;
    @NotNull(message = "entrer un prenom valide")
    private String prenom;
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @Email
    private String email;
    @NotNull(message = "entrer une adresse valide")
    private String adresse;

}
