package com.app.api.mapper;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColisMapper {
    Colis toEntity(ColisRequestDTO colisRequestDTO);
    ColisResponseDTO toDTO(Colis colis);
}
