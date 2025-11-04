package com.app.api.mapper;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProduitMapper.class)

public interface ColisMapper {

    @Mapping(source = "livreurId",target = "livreur", ignore = true)
    @Mapping(source = "clientExpediteurId",target = "clientExpediteur", ignore = true)
    @Mapping(source = "destinataireId",target = "destinataire", ignore = true)
    @Mapping(source = "zoneId",target = "zone" , ignore = true)
    Colis toEntity(ColisRequestDTO colisRequestDTO);

    ColisResponseDTO toDTO(Colis colis);

}
