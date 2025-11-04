package com.app.api.mapper;

import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonRequestDTO;
import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonResponseDTO;
import com.app.api.entity.HistoriqueLivraison;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel= "spring")
public interface HistoriqueLivraisonMapper {
    HistoriqueLivraison toEntity(HistoriqueLivraisonRequestDTO historiqueLivraisonRequestDTO);

    Set<HistoriqueLivraisonResponseDTO> toDTO(Set<HistoriqueLivraison> historiqueLivraisonSet);
}
