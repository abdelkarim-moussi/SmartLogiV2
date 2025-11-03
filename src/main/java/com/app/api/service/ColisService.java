package com.app.api.service;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.ColisMapper;
import com.app.api.repository.ColisRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ColisService {
    private ColisRepository colisRepository;
    private ColisMapper colisMapper;

    public ColisService(ColisRepository colisRepository, ColisMapper colisMapper){
        this.colisRepository = colisRepository;
        this.colisMapper = colisMapper;
    }

    public ColisResponseDTO createColis(ColisRequestDTO colisRequestDTO){

        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide "+null);
        }

        Colis colis = colisMapper.toEntity(colisRequestDTO);
        Colis createdColis = colisRepository.save(colis);

        return colisMapper.toDTO(createdColis);

    }

    public ColisResponseDTO update(String id,ColisRequestDTO colisRequestDTO){

        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalid id : "+id);
        }
        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide "+null);
        }

        Colis existungolis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis trouver avec id : "+id)
        );

        existungolis.setAdresse(colisRequestDTO.getAdresse());
        existungolis.setPoids(colisRequestDTO.getPoids());
        existungolis.setDescription(colisRequestDTO.getDescription());
        existungolis.setPriority(colisRequestDTO.getPriority());
        existungolis.setStatus(colisRequestDTO.getStatus());
        existungolis.setVilleDestination(colisRequestDTO.getVilleDestination());

        Colis updatedColis = colisRepository.save(existungolis);

        return colisMapper.toDTO(updatedColis);
    }
}
