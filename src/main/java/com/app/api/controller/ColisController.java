package com.app.api.controller;

import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.enums.ColisStatus;
import com.app.api.service.ColisService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @GetMapping
    public ResponseEntity<Page<ColisResponseDTO>> getAllColis(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "status") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "DESC") String sortDir,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "ville", required = false) String ville,
            @RequestParam(name = "zone", required = false) String zone,
            @RequestParam(name = "search", required = false) String search
    ){
        ColisFilterDTO filters = ColisFilterDTO.builder()
                .status(status)
                .priority(priority)
                .ville(ville)
                .zone(zone)
                .search(search)
                .build();

        Page<ColisResponseDTO> colisList = colisService.getAllColis(
                filters,
                page,
                size,
                sortBy,
                sortDir
        );
        return ResponseEntity.ok(colisList);
    }

    @GetMapping("/livreurs")
    public ResponseEntity<List<ColisResponseDTO>> getLivreurColis(@PathParam(value = "id") String id){
        List<ColisResponseDTO> livreurColis = colisService.getColisByLivreur(id);
        return ResponseEntity.ok(livreurColis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColisResponseDTO> getOneColis(@PathVariable("id") String id){
        ColisResponseDTO colis = colisService.getColisById(id);
        return ResponseEntity.ok(colis);
    }

    @PostMapping("/create")
    public ColisResponseDTO createColis(@RequestBody @Valid ColisRequestDTO colisRequestDTO){
        ColisResponseDTO createdColis = colisService.createColis(colisRequestDTO);
        return createdColis;
    }

    @PutMapping("/{id}/update")
    public ColisResponseDTO updateColis(@PathVariable("id") String id, @RequestBody @Valid ColisRequestDTO colisRequestDTO){
        ColisResponseDTO updatedColis = colisService.updateColis(id,colisRequestDTO);
        return updatedColis;
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteColis(@PathVariable("id") String id){
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status/{status}")
    public ColisResponseDTO updateColisStatus(@PathVariable("id") String id, @PathVariable("status") String status){
        ColisResponseDTO updatedColis = colisService.updateColisStatus(id,ColisStatus.valueOf(status));
        return updatedColis;
    }
}
