package com.app.api.mapper;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColisMapper {
    @Mapping(source = "poids",target = "poids")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "adresse",target = "adresse")
    @Mapping(source = "livreurId" ,target ="livreur")
    @Mapping(source = "expediteurId" ,target ="expediteur")
    @Mapping(source = "destinataireId" ,target ="destinataire")
    @Mapping(source = "zoneId" ,target ="zone")
    Colis toEntity(ColisRequestDTO colisRequestDTO);

    ColisResponseDTO toDTO(Colis colis);

    default Livreur mapLivreur(String livreurId){
        if(livreurId == null || livreurId.trim().isEmpty()){
            return null;
        }
        Livreur livreur = new Livreur();
        livreur.setId(livreurId);
        return livreur;
    }

    default Expediteur mapExpediteur(String expediteurId){
        if(expediteurId == null || expediteurId.trim().isEmpty()){
            return null;
        }
        Expediteur expediteur = new Expediteur();
        expediteur.setId(expediteurId);
        return expediteur;
    }

    default Destinataire mapDestinataire(String destinataireId){
        if(destinataireId == null || destinataireId.trim().isEmpty()){
            return null;
        }
        Destinataire destinataire = new Destinataire();
        destinataire.setId(destinataireId);
        return destinataire;
    }

    default Zone mapZone(String zoneId){
        if(zoneId == null || zoneId.trim().isEmpty()){
            return null;
        }
        Zone zone = new Zone();
        zone.setId(zoneId);
        return zone;
    }
}
