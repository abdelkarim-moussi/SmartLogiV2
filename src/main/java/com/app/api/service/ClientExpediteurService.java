package com.app.api.service;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.entity.ClientExpediteur;
import com.app.api.exception.InvalidDataException;
import com.app.api.mapper.ClientExpediteurMapper;
import com.app.api.repository.ClientExpediteurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Slf4j
@Service
@Transactional
public class ClientExpediteurService {

    ClientExpediteurRepository clientExpediteurRepository;
    ClientExpediteurMapper clientExpediteurMapper;
    public ClientExpediteurService(ClientExpediteurRepository clientExpediteurRepository, ClientExpediteurMapper clientExpediteurMapper){
        this.clientExpediteurRepository = clientExpediteurRepository;
        this.clientExpediteurMapper = clientExpediteurMapper;
    }

    public Page<ClientExpediteurResponseDTO> getAllClients(int page, int size){

        Pageable pageable = PageRequest.of(page,size);

        Page<ClientExpediteurResponseDTO> clients = clientExpediteurRepository.findAll(pageable)
                .map(clientExpediteurMapper::toDTO);

        return clients;
    }

    public ClientExpediteurResponseDTO createClientExpediteur(ClientExpediteurRequestDTO clientExpediteurRequestDTO){
        if(clientExpediteurRequestDTO != null){
            ClientExpediteur existingClient = clientExpediteurRepository.findByEmail(clientExpediteurRequestDTO.getEmail()).orElse(null);

            if(existingClient == null) {
                ClientExpediteur createdClient = clientExpediteurRepository.save(clientExpediteurMapper.toEntity(clientExpediteurRequestDTO));
                return clientExpediteurMapper.toDTO(createdClient);
            }

            return null;

        }else throw new InvalidDataException("les données de création ne peut être pas null");
    }

    public ClientExpediteurResponseDTO getOneClient(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id ne peut pas être null");
        }

        ClientExpediteur client = clientExpediteurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("aucun client exist avec cet id "+id)
        );

        return clientExpediteurMapper.toDTO(client);
    }

    public ClientExpediteurResponseDTO updateClientExpediteur(String id,ClientExpediteurRequestDTO clientExpediteurRequestDTO){
        if(id == null || id.trim().isEmpty()){
            throw new IllegalArgumentException("client id ne peut pas être null");
        }

        if(clientExpediteurRequestDTO != null){
            ClientExpediteur existingClient = clientExpediteurRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("aucun client avec cet id "+id)
            );

            existingClient.setNom(clientExpediteurRequestDTO.getNom());
            existingClient.setPrenom(clientExpediteurRequestDTO.getPrenom());
            existingClient.setTelephone(clientExpediteurRequestDTO.getTelephone());
            existingClient.setEmail(clientExpediteurRequestDTO.getEmail());
            existingClient.setAdresse(clientExpediteurRequestDTO.getAdresse());

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
