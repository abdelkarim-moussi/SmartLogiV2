package com.app.api.service;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.LivreurMapper;
import com.app.api.entity.Livreur;
import com.app.api.repository.LivreurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LivreurService {

    LivreurMapper livreurMapper;
    LivreurRepository livreurRepository;

    public LivreurService(LivreurMapper livreurMapper, LivreurRepository livreurRepository){
        this.livreurMapper = livreurMapper;
        this.livreurRepository = livreurRepository;
    }

    public LivreurResponseDTO createLivreur(LivreurRequestDTO livreurRequestDTO){

        if(livreurRequestDTO == null){
            return null;
        }

        Livreur livreur = livreurMapper.toEntity(livreurRequestDTO);
        Livreur createdLivreur = livreurRepository.save(livreur);

        return livreurMapper.toDTO(createdLivreur);

    }

    public boolean deleteLivreur(String id){
        if(id == null || id.trim().isEmpty()) return false;
        livreurRepository.deleteById(id);
        return true;
    }

    public LivreurResponseDTO updateLivreur(String id,LivreurRequestDTO livreurRequestDTO){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("id invalide id = "+id);
        }
        if(livreurRequestDTO == null) {
            throw new InvalidDataException("données invalid = "+livreurRequestDTO);
        }

        Livreur existingLivreur = livreurRepository.findById(id).orElse(null);
        if(existingLivreur == null) {
            throw new ResourceNotFoundException("aucun livreur trouver avec id = "+id);
        }

        existingLivreur.setNom(livreurRequestDTO.getNom());
        existingLivreur.setPrenom(livreurRequestDTO.getPrenom());
        existingLivreur.setTelephone(livreurRequestDTO.getTelephone());
        existingLivreur.setEmail(livreurRequestDTO.getEmail());
        existingLivreur.setVehicule(livreurRequestDTO.getVehicule());

        Livreur livreur = livreurRepository.save(existingLivreur);

        return livreurMapper.toDTO(livreur);
    }

    public LivreurResponseDTO getOneLivreur(String id){
        if(id == null || id.trim().isEmpty()) return null;

        Livreur livreur = livreurRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("aucun livreur trouver avec id = "+id));
        return livreurMapper.toDTO(livreur);

    }

    public List<LivreurResponseDTO> getAllLivreur(){

        List<Livreur> livreurs = livreurRepository.findAll();
        List<LivreurResponseDTO> livreurResponseDTOS = new ArrayList<>();

        if(livreurs.isEmpty()) throw new ResourceNotFoundException("aucun livreur disponible");

        livreurs.forEach(livreur ->
                livreurResponseDTOS.add(livreurMapper.toDTO(livreur))
                );

        return livreurResponseDTOS;
    }

}
