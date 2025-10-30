package com.app.SmartLogiV2.controller;

import com.app.SmartLogiV2.dto.livreurDTO.LivreurRequestDTO;
import com.app.SmartLogiV2.dto.livreurDTO.LivreurResponseDTO;
import com.app.SmartLogiV2.service.LivreurService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {
    private LivreurService livreurService;

    public LivreurController(LivreurService livreurService){
        this.livreurService = livreurService;
    }

    @PostMapping
    LivreurResponseDTO getAllLivreur(@RequestBody LivreurRequestDTO livreurRequestDTO){
        LivreurResponseDTO livreurResponseDTO = livreurService.createLivreur(livreurRequestDTO);
        return livreurResponseDTO;
    }
}
