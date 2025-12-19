package com.app.api.controller;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.service.LivreurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/livreurs")
@RequiredArgsConstructor
public class LivreurController {
    private final LivreurService livreurService;

    @GetMapping()
    public List<LivreurResponseDTO> getAllLivreur(){
        return livreurService.getAllLivreur();
    }

    @GetMapping("/{id}")
    public LivreurResponseDTO getOneLivreur(@PathVariable("id") String id){
        return livreurService.getOneLivreur(id);
    }

    @PostMapping("/create")
    public LivreurResponseDTO createLivreur(@RequestBody @Valid LivreurRequestDTO livreurRequestDTO){
        return livreurService.createLivreur(livreurRequestDTO);
    }

    @PutMapping("/{id}/update")
    public LivreurResponseDTO updateLivreur(@PathVariable("id") String id, @RequestBody @Valid LivreurRequestDTO livreurRequestDTO){
        return livreurService.updateLivreur(id,livreurRequestDTO);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteLivreur(@PathVariable("id") String id){
        livreurService.deleteLivreur(id);
        return "livreur avec id : "+id+" supprimer avec success";
    }
}
