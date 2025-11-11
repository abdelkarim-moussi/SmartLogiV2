package com.app.api.controller;

import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.service.LivraisonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/livraison")
public class LivraisonController {
    LivraisonService livraisonService;
    public LivraisonController(LivraisonService livraisonService){
        this.livraisonService = livraisonService;
    }
    @PostMapping("/{colis_id}/livreur/{livreur_id}")
    ResponseEntity<ColisResponseDTO> assignLivreurToColis(@PathVariable("colis_id") String colisId,
                                                          @PathVariable("livreur_id") String livreurId){
       ColisResponseDTO colis = livraisonService.assignLivreurToColis(colisId,livreurId);
       return ResponseEntity.ok(colis);
    }
}
