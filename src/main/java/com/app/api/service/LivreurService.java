package com.app.api.service;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.entity.Role;
import com.app.api.entity.User;
import com.app.api.exception.AlreadyExistException;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.NotFoundException;
import com.app.api.mapper.LivreurMapper;
import com.app.api.entity.Livreur;
import com.app.api.repository.LivreurRepository;
import com.app.api.repository.UserRepository;
import com.app.api.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LivreurService {

    private final LivreurMapper livreurMapper;
    private final LivreurRepository livreurRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public LivreurResponseDTO createLivreur(LivreurRequestDTO request){

        if(request == null){
            throw new InvalidDataException("données invalide "+null);
        }

        boolean existsByEmail = userRepository.existsByEmail(request.getNonUtilisateur());

        if(existsByEmail){
            throw new AlreadyExistException("user name already used");
        }

        Set<Role> roles = Set.of(Role.builder()
                .name("LIVREUR").build());

        User user = userService.addUserHelper(request.getNonUtilisateur(),request.getPassword(),roles);

        Livreur livreur = livreurMapper.toEntity(request);

        livreur.setUser(user);

        return livreurMapper.toDTO(livreurRepository.save(livreur));

    }

    public void deleteLivreur(String id){
        if(id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("id invalide id = "+id);
        };

        livreurRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("aucun livreur avec cet id")
        );
        livreurRepository.deleteById(id);
    }

    public LivreurResponseDTO updateLivreur(String id,LivreurRequestDTO livreurRequestDTO){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("id invalide id = "+id);
        }
        if(livreurRequestDTO == null) {
            throw new InvalidDataException("données invalid = "+livreurRequestDTO);
        }

        Livreur existingLivreur = livreurRepository.findById(id).orElseThrow(
                () -> new NotFoundException("aucun livreur trouver avec id = "+id)
        );

        existingLivreur.setNom(livreurRequestDTO.getNom());
        existingLivreur.setPrenom(livreurRequestDTO.getPrenom());
        existingLivreur.setTelephone(livreurRequestDTO.getTelephone());
        existingLivreur.setEmail(livreurRequestDTO.getEmail());
        existingLivreur.setVehicule(livreurRequestDTO.getVehicule());

        Livreur livreur = livreurRepository.save(existingLivreur);

        return livreurMapper.toDTO(livreur);
    }

    @Transactional(readOnly = true)
    public LivreurResponseDTO getOneLivreur(String id){
        if(id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("invalide id = "+id);
        };

        Livreur livreur = livreurRepository.findById(id).
                orElseThrow(()->new NotFoundException("aucun livreur trouver avec id = "+id));
        return livreurMapper.toDTO(livreur);

    }

    @Transactional(readOnly = true)
    public List<LivreurResponseDTO> getAllLivreur(){

        List<Livreur> livreurs = livreurRepository.findAll();
        List<LivreurResponseDTO> livreurResponseDTOS = new ArrayList<>();

        if(livreurs.isEmpty()) throw new NotFoundException("aucun livreur disponible");

        livreurs.forEach(livreur ->
                livreurResponseDTOS.add(livreurMapper.toDTO(livreur))
                );

        return livreurResponseDTOS;
    }

}
