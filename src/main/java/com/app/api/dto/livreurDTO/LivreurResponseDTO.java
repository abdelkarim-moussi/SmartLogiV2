package com.app.api.dto.livreurDTO;

import com.app.api.dto.zoneDTO.ZoneResponseDTO;
import com.app.api.entity.Zone;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivreurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String vehicule;
    private ZoneResponseDTO zone;
}
