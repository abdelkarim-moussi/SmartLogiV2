package com.app.api.service;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisStatus;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.ColisMapper;
import com.app.api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ColisService {
    private final LivreurRepository livreurRepository;
    private final ClientExpediteurRepository clientExpediteurRepository;
    private final DestinataireRepository destinataireRepository;
    private final ZoneRepository zoneRepository;
    private ColisRepository colisRepository;
    private ColisMapper colisMapper;

    public ColisService(ColisRepository colisRepository, ColisMapper colisMapper, LivreurRepository livreurRepository, ClientExpediteurRepository clientExpediteurRepository, DestinataireRepository destinataireRepository, ZoneRepository zoneRepository){
        this.colisRepository = colisRepository;
        this.colisMapper = colisMapper;
        this.livreurRepository = livreurRepository;
        this.clientExpediteurRepository = clientExpediteurRepository;
        this.destinataireRepository = destinataireRepository;
        this.zoneRepository = zoneRepository;
    }

    public ColisResponseDTO createColis(ColisRequestDTO colisRequestDTO){

        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide "+null);
        }

        Colis colis = colisMapper.toEntity(colisRequestDTO);
        setRelations(colisRequestDTO,colis);
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

        setRelations(colisRequestDTO,existingColis);
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

    public ColisResponseDTO getOneColisById(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invcalide id :"+id);
        }

        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis disponible avec cet id : "+id)
        );

        return colisMapper.toDTO(colis);

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

    private void setRelations(ColisRequestDTO colisRequestDTO, Colis colis){
        if(colisRequestDTO.getLivreurId() != null){
            Livreur livreur = livreurRepository.findById(colisRequestDTO.getLivreurId()).orElseThrow(
                    () -> new ResourceNotFoundException("aucun livreur disponible avec cet id"+colisRequestDTO.getLivreurId())
            );
            colis.setLivreur(livreur);
        }

        if(colisRequestDTO.getClientExpediteurId() != null){
            ClientExpediteur expediteur = clientExpediteurRepository.findById(colisRequestDTO.getClientExpediteurId()).orElseThrow(
                    () -> new ResourceNotFoundException("aucun expediteur trouver avec cet id = "+colisRequestDTO.getClientExpediteurId())
            );
            colis.setClientExpediteur(expediteur);
        }

        if(colisRequestDTO.getDestinataireId() != null){
            Destinataire destinataire = destinataireRepository.findById(colisRequestDTO.getDestinataireId()).orElseThrow(
                    () -> new ResourceNotFoundException("aucun destinataire trouver avec cet id = "+colisRequestDTO.getDestinataireId())
            );
            colis.setDestinataire(destinataire);
        }

        if(colisRequestDTO.getZoneId() != null){
            Zone zone = zoneRepository.findById(colisRequestDTO.getZoneId()).orElseThrow(
                    () -> new ResourceNotFoundException("aucune zone trouver avec cet id = "+colisRequestDTO.getZoneId())
            );
            colis.setZone(zone);
        }
    }
}
