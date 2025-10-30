package com.app.SmartLogiV2.service;

import com.app.SmartLogiV2.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.mapper.LivreurMapper;
import com.app.SmartLogiV2.entity.Livreur;
import com.app.SmartLogiV2.repository.LivreurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LivreurService {

    LivreurMapper livreurMapper;
    LivreurRepository livreurRepository;

    public LivreurService(LivreurMapper livreurMapper, LivreurRepository livreurRepository){
        this.livreurMapper = livreurMapper;
        this.livreurRepository = livreurRepository;
    }

    @Transactional
    public LivreurResponseDTO createLivreur(LivreurRequestDTO livreurRequestDTO){

        if(livreurRequestDTO == null){
            return null;
        }

        Livreur livreur = livreurMapper.toEntity(livreurRequestDTO);
        Livreur createdLivreur = livreurRepository.save(livreur);

        return livreurMapper.toDTO(createdLivreur);

    }
}
