package com.app.api.dto.livreurDTO;

import com.app.api.dto.zoneDTO.ZoneResponseDTO;
import com.app.api.entity.Zone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LivreurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String vehicule;
    private ZoneResponseDTO zone;
}
