package com.app.api.unit.service;

import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import com.app.api.exception.NotFoundException;
import com.app.api.mapper.ColisMapper;
import com.app.api.repository.ColisRepository;
import com.app.api.repository.LivreurRepository;
import com.app.api.service.LivraisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivraisonServiceUnitTest {

    private final String COLIS_ID = "C001";
    private final String LIVREUR_ID = "L001";

    @Mock
    private ColisRepository colisRepository;
    @Mock
    private LivreurRepository livreurRepository;
    @Mock
    private ColisMapper colisMapper;

    @InjectMocks
    private LivraisonService livraisonService;

    private Colis colisEntity;
    private Livreur livreurEntity;
    private ColisResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        colisEntity = Colis.builder().id(COLIS_ID).livreur(null).build();
        livreurEntity = new Livreur();
        livreurEntity.setId(LIVREUR_ID);
        responseDTO = ColisResponseDTO.builder().id(COLIS_ID).build();
    }

    @Test
    void assignLivreurToColis_shouldAssignLivreur_whenColisIsUnassigned() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));

        when(colisMapper.toDTO(any(Colis.class))).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID);

        // Assert
        assertNotNull(result);
        assertEquals(livreurEntity, colisEntity.getLivreur());

        verify(colisRepository, never()).save(any());
        verify(colisMapper).toDTO(colisEntity);
    }

    @Test
    void assignLivreurToColis_whenColisNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID)
        );
    }

    @Test
    void assignLivreurToColis_whenLivreurNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID)
        );
    }

    @Test
    void assignLivreurToColis_whenAlreadyAssigned_shouldThrowIllegalStateException() {
        // Arrange
        colisEntity.setLivreur(new Livreur()); // Already assigned
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
                livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID)
        );
    }
}