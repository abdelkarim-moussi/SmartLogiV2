package com.app.api.controller;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.exception.InvalidDataException;
import com.app.api.service.LivreurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {
    private LivreurService livreurService;

    public LivreurController(LivreurService livreurService){
        this.livreurService = livreurService;
    }

    @GetMapping()
    List<LivreurResponseDTO> getAllLivreur(){
        List<LivreurResponseDTO> livreurs = livreurService.getAllLivreur();
        return livreurs;
    }

    @GetMapping("/{id}")
    LivreurResponseDTO getOneLivreur(@PathVariable("id") String id){
        LivreurResponseDTO livreurResponseDTO = livreurService.getOneLivreur(id);
        return livreurResponseDTO;
    }

    @PostMapping
    LivreurResponseDTO createLivreur(@RequestBody LivreurRequestDTO livreur){
        LivreurResponseDTO livreurResponseDTO = livreurService.createLivreur(livreur);
        return livreurResponseDTO;
    }

    @PutMapping("/{id}/update")
    LivreurResponseDTO updateLivreur(@PathVariable("id") String id,@RequestBody LivreurRequestDTO livreurRequestDTO){
        LivreurResponseDTO livreurResponseDTO = livreurService.updateLivreur(id,livreurRequestDTO);
        return livreurResponseDTO;
    }

    @DeleteMapping("/{id}/delete")
    String deleteLivreur(@PathVariable("id") String id){
        livreurService.deleteLivreur(id);
        return "livreur avec id : "+id+" supprimer avec succes";
    }
}
