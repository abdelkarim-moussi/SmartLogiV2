package com.app.api.dto.clientExpediteurDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientExpediteurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;

}
