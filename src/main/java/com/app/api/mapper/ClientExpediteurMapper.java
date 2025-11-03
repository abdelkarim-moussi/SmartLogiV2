package com.app.api.mapper;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.entity.ClientExpediteur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {

    ClientExpediteur toEntity(ClientExpediteurRequestDTO clientExpediteurRequestDTO);

    ClientExpediteurResponseDTO toDTO(ClientExpediteur client);
}
