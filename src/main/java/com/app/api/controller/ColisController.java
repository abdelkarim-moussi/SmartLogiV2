package com.app.api.controller;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.service.ColisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @GetMapping
    List<ColisResponseDTO> getAllColis(){
        List<ColisResponseDTO> colisList = colisService.getAllColis();
        return colisList;
    }

    @PostMapping("/create")
    ColisResponseDTO createColis(@RequestBody @Valid ColisRequestDTO colisRequestDTO){
        ColisResponseDTO createdColis = colisService.createColis(colisRequestDTO);
        return createdColis;
    }

    @PutMapping("/{id}/update")
    ColisResponseDTO updateColis(@PathVariable("id") String id, @RequestBody @Valid ColisRequestDTO colisRequestDTO){
        ColisResponseDTO updatedColis = colisService.updateColis(id,colisRequestDTO);
        return updatedColis;
    }

    @DeleteMapping("/{id}/delete")
    ResponseEntity<Void> deleteColis(@PathVariable("id") String id){
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }
}
