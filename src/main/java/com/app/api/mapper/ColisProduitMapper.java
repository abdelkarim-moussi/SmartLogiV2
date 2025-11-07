package com.app.api.mapper;

import com.app.api.dto.colisProduitDTO.ColisProduitRequestDTO;
import com.app.api.dto.colisProduitDTO.ColisProduitResponseDTO;
import com.app.api.entity.ColisProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;

@Mapper(componentModel = "spring",uses = ProduitMapper.class)
public interface ColisProduitMapper {

    @Mapping(target = "produit",ignore = true)
    @Mapping(target = "colis",ignore = true)
    ColisProduit toEntity(ColisProduitRequestDTO colisProduit);

    ColisProduitResponseDTO toDTO(ColisProduit colisProduit);
    Set<ColisProduitResponseDTO> toDTOSet(Set<ColisProduit> colisProduitSet);
}
