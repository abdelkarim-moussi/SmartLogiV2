package com.app.api.mapper;

import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonRequestDTO;
import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonResponseDTO;
import com.app.api.entity.HistoriqueLivraison;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface HistoriqueLivraisonMapper {
    HistoriqueLivraison toEntity(HistoriqueLivraisonRequestDTO historiqueLivraisonRequestDTO);

    HistoriqueLivraisonResponseDTO toDTO(HistoriqueLivraison historiqueLivraison);
}
