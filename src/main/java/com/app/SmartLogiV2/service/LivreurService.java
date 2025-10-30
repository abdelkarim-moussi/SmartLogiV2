package com.app.SmartLogiV2.service;

import com.app.SmartLogiV2.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.exception.LivreurNotFoundExpception;
import com.app.SmartLogiV2.mapper.LivreurMapper;
import com.app.SmartLogiV2.entity.Livreur;
import com.app.SmartLogiV2.repository.LivreurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(id == null || id.trim().isEmpty()) return null;
        if(livreurRequestDTO == null) return null;

        Livreur livreur = livreurRepository.save(livreurMapper.toEntity(livreurRequestDTO));

        return livreurMapper.toDTO(livreur);
    }

    public LivreurResponseDTO getOneLivreur(String id){
        if(id == null || id.trim().isEmpty()) return null;

        Livreur livreur = livreurRepository.findById(id).
                orElseThrow(()->new LivreurNotFoundExpception("aucun livreur avec id :"+id +" trouver!"));

        return livreurMapper.toDTO(livreur);

    }

    public List<LivreurResponseDTO> getAllLivreur(){

        List<Livreur> livreurs = livreurRepository.findAll();
        List<LivreurResponseDTO> livreurResponseDTOS = new ArrayList<>();

        if(livreurs.isEmpty()) return null;

        livreurs.forEach(livreur ->
                livreurResponseDTOS.add(livreurMapper.toDTO(livreur))
                );

        return livreurResponseDTOS;
    }

}
