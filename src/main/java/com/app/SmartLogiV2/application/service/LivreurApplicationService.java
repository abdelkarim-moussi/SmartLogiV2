package com.app.SmartLogiV2.application.service;

import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.application.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.application.mapper.LivreurMapper;
import com.app.SmartLogiV2.application.mapper.LivreurMapperImpl;
import com.app.SmartLogiV2.domain.model.Livreur;
import com.app.SmartLogiV2.domain.repository.LivreurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LivreurApplicationService {

    LivreurMapper livreurMapper;
    LivreurRepository livreurRepository;

    public LivreurApplicationService(LivreurMapper livreurMapper, LivreurRepository livreurRepository){
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
}
