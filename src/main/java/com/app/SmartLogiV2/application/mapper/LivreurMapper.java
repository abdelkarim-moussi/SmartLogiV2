package com.app.SmartLogiV2.application.mapper;

import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.domain.model.Livreur;

public interface LivreurMapper extends GenericMapper<Livreur, LivreurRequestDTO,LivreurResponseDTO> {

    @Override
    Livreur toEntity(LivreurRequestDTO livreurRequestDTO);

    @Override
    LivreurResponseDTO toDTO(Livreur livreur);
}
