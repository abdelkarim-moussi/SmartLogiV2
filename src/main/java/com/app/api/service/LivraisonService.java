package com.app.api.service;

import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import com.app.api.mapper.ColisMapper;
import com.app.api.mapper.LivreurMapper;
import com.app.api.repository.ColisRepository;
import com.app.api.repository.LivreurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class LivraisonService {
    ColisRepository colisRepository;
    LivreurRepository livreurRepository;
    ColisMapper colisMapper;
    LivreurMapper livreurMapper;
    public LivraisonService(ColisRepository colisRepository
            ,LivreurRepository livreurRepository
            ,ColisMapper colisMapper,
            LivreurMapper livreurMapper){
        this.colisRepository = colisRepository;
        this.livreurRepository = livreurRepository;
        this.colisMapper = colisMapper;
        this.livreurMapper = livreurMapper;
    }

    public ColisResponseDTO assignLivreurToColis(String colisId, String livreurId){
        if(colisId == null || colisId.trim().isEmpty() || livreurId == null || livreurId.trim().isEmpty()){
            throw new IllegalArgumentException("colis id ou livreur id ne peut pas être null");
        }

        Colis colis = colisRepository.findById(colisId).orElseThrow(
                () -> new EntityNotFoundException("aucune colis avec cet id "+colisId)
        );

        Livreur livreur = livreurRepository.findById(livreurId).orElseThrow(
                () -> new EntityNotFoundException("aucun livreur avec cet id "+livreurId)
            );
        if(colis.getLivreur() != null && colis.getLivreur().getId() == livreurId){
            return colisMapper.toDTO(colis);
        }
        else if(colis.getLivreur() != null){
            throw new IllegalStateException("cete colis est déja assiggner a un livreur");
        }

        colis.setLivreur(livreur);
        Colis updatedColis = colisRepository.save(colis);

        return colisMapper.toDTO(updatedColis);

    }
}
