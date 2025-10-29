package com.app.SmartLogiV2.application.mapper;

import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.domain.model.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivreurMapper {

    @Mapping(source = "zoneId",target = "zone.id")
    Livreur toEntity(LivreurRequestDTO livreurRequestDTO);

    LivreurResponseDTO toDTO(Livreur livreur);
}
