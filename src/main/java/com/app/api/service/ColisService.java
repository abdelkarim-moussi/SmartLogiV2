package com.app.api.service;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.enums.ColisStatus;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.ColisMapper;
import com.app.api.repository.ColisRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ColisResponseDTO updateColis(String id,ColisRequestDTO colisRequestDTO){

        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalid id : "+id);
        }
        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide "+null);
        }

        Colis existingColis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis trouver avec id : "+id)
        );

        existingColis.setAdresse(colisRequestDTO.getAdresse());
        existingColis.setPoids(colisRequestDTO.getPoids());
        existingColis.setDescription(colisRequestDTO.getDescription());
        existingColis.setPriority(colisRequestDTO.getPriority());
        existingColis.setStatus(colisRequestDTO.getStatus());
        existingColis.setVilleDestination(colisRequestDTO.getVilleDestination());

        Colis updatedColis = colisRepository.save(existingColis);

        return colisMapper.toDTO(updatedColis);
    }

    public List<ColisResponseDTO> getAllColis(){

        List<Colis> colisList = colisRepository.findAll();
        List<ColisResponseDTO> colisResponseDTOList = new ArrayList<>();

        if(!colisList.isEmpty()){
            colisList.forEach(colis ->
                    colisResponseDTOList.add(colisMapper.toDTO(colis)));
        }

        return colisResponseDTOList;
    }

    public void deleteColis(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalide id = "+id);
        }
        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis avec id = "+id)
        );

        colisRepository.deleteById(id);
    }

    public ColisResponseDTO updateColisStatus(String id,ColisStatus colisStatus){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalide id = "+id);
        }

        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis avec id = "+id)
        );

        if(colis.getStatus().equals(colisStatus)){
            throw new InvalidDataException("la colis est déja on status : "+colisStatus);
        }
        colis.setStatus(colisStatus);
        colisRepository.save(colis);
        return colisMapper.toDTO(colis);
    }
}
