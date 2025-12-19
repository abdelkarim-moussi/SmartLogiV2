package com.app.api.mapper;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.entity.ClientExpediteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {
    @Mapping(target = "colisEnvoyer", ignore = true)
    @Mapping(target = "user", ignore = true)
    ClientExpediteur toEntity(ClientExpediteurRequestDTO clientExpediteurRequestDTO);
    ClientExpediteurResponseDTO toDTO(ClientExpediteur client);
}
