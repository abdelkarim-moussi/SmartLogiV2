package com.app.api.service;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.entity.ClientExpediteur;
import com.app.api.entity.Role;
import com.app.api.entity.User;
import com.app.api.exception.AlreadyExistException;
import com.app.api.exception.InvalidDataException;
import com.app.api.mapper.ClientExpediteurMapper;
import com.app.api.repository.ClientExpediteurRepository;
import com.app.api.repository.RoleRepository;
import com.app.api.security.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientExpediteurService {

    private final ClientExpediteurRepository clientExpediteurRepository;
    private final ClientExpediteurMapper clientExpediteurMapper;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<ClientExpediteurResponseDTO> getAllClients(int page, int size){

        Pageable pageable = PageRequest.of(page,size);

        return clientExpediteurRepository.findAll(pageable)
                .map(clientExpediteurMapper::toDTO);
    }

    public ClientExpediteurResponseDTO createClientExpediteur(ClientExpediteurRequestDTO request){
        if(request == null) {
            throw new InvalidDataException("les données de création ne peut être pas null");
        }

        boolean existingClient = clientExpediteurRepository
                .existsByEmail(request.getEmail());

        if(existingClient) {
            throw new AlreadyExistException("email already used "+request.getEmail());
        }

        ClientExpediteur client = clientExpediteurMapper.toEntity(request);

        Set<Role> roles = Set.of(Role.builder().name("CLIENT").build());

        User user = userService.addUserHelper(request.getEmail(), request.getPassword(),roles);

        client.setUser(user);
        return clientExpediteurMapper.toDTO(client);

    }

    @Transactional(readOnly = true)
    public ClientExpediteurResponseDTO getOneClient(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id ne peut pas être null");
        }

        ClientExpediteur client = clientExpediteurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("aucun client exist avec cet id "+id)
        );

        return clientExpediteurMapper.toDTO(client);
    }

    public ClientExpediteurResponseDTO updateClientExpediteur(String id,ClientExpediteurRequestDTO request){
        if(id == null || id.trim().isEmpty()){
            throw new IllegalArgumentException("client id ne peut pas être null");
        }

        if(request != null){
            ClientExpediteur existingClient = clientExpediteurRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("aucun client avec cet id "+id)
            );

            existingClient.setNom(request.getNom());
            existingClient.setPrenom(request.getPrenom());
            existingClient.setTelephone(request.getTelephone());
            existingClient.setEmail(request.getEmail());
            existingClient.setAdresse(request.getAdresse());

            ClientExpediteur updatedClient = clientExpediteurRepository.save(existingClient);
            return clientExpediteurMapper.toDTO(updatedClient);

        }else throw new InvalidDataException("les données de création ne peut être pas null");
    }

    public void deleteClientExpediteur(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id ne peut pas être null");
        }

        ClientExpediteur existingClient = clientExpediteurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("aucun client avec cet id "+id)
        );

        clientExpediteurRepository.deleteById(existingClient.getId());
    }
}
