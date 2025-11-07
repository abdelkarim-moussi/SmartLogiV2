package com.app.api.mapper;
import com.app.api.dto.produitDTO.ProduitDTO;
import com.app.api.entity.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    Produit toEntity(ProduitDTO produit);

    ProduitDTO toDTO(Produit produit);

}
