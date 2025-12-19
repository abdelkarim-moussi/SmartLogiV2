package com.app.api.service;

import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import com.app.api.exception.NotFoundException;
import com.app.api.mapper.ColisMapper;
import com.app.api.mapper.LivreurMapper;
import com.app.api.repository.ColisRepository;
import com.app.api.repository.LivreurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LivraisonService {
    private final ColisRepository colisRepository;
    private final LivreurRepository livreurRepository;
    private final ColisMapper colisMapper;

    public ColisResponseDTO assignLivreurToColis(String colisId, String livreurId){
        if(colisId == null || colisId.trim().isEmpty() || livreurId == null || livreurId.trim().isEmpty()){
            throw new IllegalArgumentException("colis id ou livreur id ne peut pas être null");
        }

        Colis colis = colisRepository.findById(colisId).orElseThrow(
                () -> new NotFoundException("aucune colis trouver avec id "+colisId)
        );

        Livreur livreur = livreurRepository.findById(livreurId).orElseThrow(
                () -> new NotFoundException("aucun livreur trouver avec id "+livreurId)
            );

        if(colis.getLivreur() != null){
            throw new IllegalStateException("cette colis est déja assigner a un livreur");
        }

        else {
            colis.setLivreur(livreur);
            return colisMapper.toDTO(colis);
        }

    }
}
