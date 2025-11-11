package com.app.api.controller;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.service.LivreurService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @PostMapping("/create")
    LivreurResponseDTO createLivreur(@RequestBody @Valid LivreurRequestDTO livreurRequestDTO){
        LivreurResponseDTO livreurResponseDTO = livreurService.createLivreur(livreurRequestDTO);
        return livreurResponseDTO;
    }

    @PutMapping("/{id}/update")
    LivreurResponseDTO updateLivreur(@PathVariable("id") String id,@RequestBody @Valid LivreurRequestDTO livreurRequestDTO){
        LivreurResponseDTO livreurResponseDTO = livreurService.updateLivreur(id,livreurRequestDTO);
        return livreurResponseDTO;
    }

    @DeleteMapping("/{id}/delete")
    String deleteLivreur(@PathVariable("id") String id){
        livreurService.deleteLivreur(id);
        return "livreur avec id : "+id+" supprimer avec succes";
    }
}
