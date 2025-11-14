package com.app.api.dto.clientExpediteurDTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientExpediteurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String adresse;

}
