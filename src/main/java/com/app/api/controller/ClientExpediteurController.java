package com.app.api.controller;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.service.ClientExpediteurService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientExpediteurController {
    ClientExpediteurService clientExpediteurService;
    public ClientExpediteurController(ClientExpediteurService clientExpediteurService){
        this.clientExpediteurService = clientExpediteurService;
    }

    @GetMapping
    public ResponseEntity<Page<ClientExpediteurResponseDTO>> getAllClientExpediteur(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        Page<ClientExpediteurResponseDTO> clients = clientExpediteurService.getAllClients(page,size);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientExpediteurResponseDTO> getClientExpediteur(@PathVariable("id") String id){
        ClientExpediteurResponseDTO client = clientExpediteurService.getOneClient(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientExpediteurResponseDTO> createClientExpediteur(@RequestBody @Valid ClientExpediteurRequestDTO clientExpediteurRequestDTO){
        ClientExpediteurResponseDTO client = clientExpediteurService.createClientExpediteur(clientExpediteurRequestDTO);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientExpediteurResponseDTO> updateClientExpediteur(@PathVariable("id") String id,
                                                                              @RequestBody @Valid ClientExpediteurRequestDTO clientExpediteurRequestDTO){
        ClientExpediteurResponseDTO client = clientExpediteurService.updateClientExpediteur(id , clientExpediteurRequestDTO);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientExpediteur(@PathVariable("id") String id){

        clientExpediteurService.deleteClientExpediteur(id);
        return ResponseEntity.ok("client supprimer avec succés");
    }


}
