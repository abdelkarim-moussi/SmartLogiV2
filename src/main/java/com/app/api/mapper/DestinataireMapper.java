package com.app.api.mapper;

import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.dto.destinataireDTO.DestinataireResponseDTO;
import com.app.api.entity.Destinataire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DestinataireMapper {

    Destinataire toEntity(DestinataireRequestDTO destinataireRequestDTO);

    DestinataireResponseDTO toDTO(Destinataire destinataire);
}
