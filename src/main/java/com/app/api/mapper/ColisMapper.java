package com.app.api.mapper;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColisMapper {
    @Mapping(source = "poids",target = "poids")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "adresse",target = "adresse")
    Colis toEntity(ColisRequestDTO colisRequestDTO);

    ColisResponseDTO toDTO(Colis colis);
}
