package com.app.api.mapper;

import com.app.api.dto.colisProduitDTO.ColisProduitRequestDTO;
import com.app.api.dto.colisProduitDTO.ColisProduitResponseDTO;
import com.app.api.entity.ColisProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColisProduitMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "produit",ignore = true)
    @Mapping(target = "colis",ignore = true)
    ColisProduit toEntity(ColisProduitRequestDTO colisProduit);

    ColisProduitResponseDTO toDTO(ColisProduit colisProduit);
    List<ColisProduitResponseDTO> toDTOList(List<ColisProduit> colisProduitList);
}
