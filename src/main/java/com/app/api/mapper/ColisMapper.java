package com.app.api.mapper;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColisMapper {

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

    default ClientExpediteur mapExpediteur(String ClientExpediteurId){
        if(ClientExpediteurId == null || ClientExpediteurId.trim().isEmpty()){
            return null;
        }
        ClientExpediteur clientExpediteur = new ClientExpediteur();
        clientExpediteur.setId(ClientExpediteurId);
        return clientExpediteur;
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
