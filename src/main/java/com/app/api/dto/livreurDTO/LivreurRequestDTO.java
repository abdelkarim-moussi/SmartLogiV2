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
    @Pattern(regexp = "0+[0-9]{9}",message = "telephone invalid (format : 0 + 9 nombre apré , ex : 0678954321")
    private String telephone;
    @Email(message = "Email Invalid")
    private String email;
    @NotBlank(message = "entrer une vehicule")
    private String vehicule;
    private Long zoneId;

    private String userName;
    private String password;
}
