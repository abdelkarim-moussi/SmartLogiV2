package com.app.api.mapper;

import com.app.api.dto.produitDTO.ProduitResponseDTO;
import com.app.api.dto.produitDTO.ProduittRequestDTO;
import com.app.api.entity.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit toEntity(ProduittRequestDTO produittRequestDTO);

    ProduitResponseDTO toDTO(Produit produit);
}
