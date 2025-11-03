package com.app.api.mapper;

import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.dto.destinataireDTO.DestinataireResponseDTO;
import com.app.api.entity.Destinataire;

public interface DestinateurMapper {

    Destinataire toEntity(DestinataireRequestDTO destinataireRequestDTO);

    DestinataireResponseDTO toDTO(Destinataire destinataire);
}
