package com.app.api.controller;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.service.ColisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @PostMapping("/create")
    ColisResponseDTO createColis(@RequestBody @Valid ColisRequestDTO colisRequestDTO){
        ColisResponseDTO createdColis = colisService.createColis(colisRequestDTO);
        return createdColis;
    }
}
