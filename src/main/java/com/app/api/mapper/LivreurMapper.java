package com.app.api.mapper;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.entity.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivreurMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "zone", ignore = true)
    @Mapping(target = "colisLivrer", ignore = true)
    Livreur toEntity(LivreurRequestDTO livreurRequestDTO);
    LivreurResponseDTO toDTO(Livreur livreur);
}
