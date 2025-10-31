package com.app.api.mapper;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.entity.Livreur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivreurMapper {

    Livreur toEntity(LivreurRequestDTO livreurRequestDTO);
    LivreurResponseDTO toDTO(Livreur livreur);
}
