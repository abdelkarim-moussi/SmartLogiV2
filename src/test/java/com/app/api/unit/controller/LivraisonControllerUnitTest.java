package com.app.api.unit.controller;

import com.app.api.controller.LivraisonController;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.service.LivraisonService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivraisonControllerUnitTest {

    private final String COLIS_ID = "C001";
    private final String LIVREUR_ID = "L001";

    @Mock
    private LivraisonService livraisonService;

    @InjectMocks
    private LivraisonController livraisonController;

    private ColisResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = ColisResponseDTO.builder()
                .id(COLIS_ID)
                .livreur(new LivreurResponseDTO())
                .build();
    }

    @Test
    void assignLivreurToColis_shouldReturnOk_whenAssignmentSucceeds() {
        // Arrange
        when(livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ColisResponseDTO> response = livraisonController.assignLivreurToColis(COLIS_ID, LIVREUR_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(COLIS_ID, response.getBody().getId());
        verify(livraisonService, times(1)).assignLivreurToColis(COLIS_ID, LIVREUR_ID);
    }

    @Test
    void assignLivreurToColis_shouldPropagateIllegalArgumentException_whenIdsAreNullOrEmpty() {
        // Arrange
        // Mock the service to throw IllegalArgumentException for bad input
        when(livraisonService.assignLivreurToColis(eq(COLIS_ID), eq(""))).thenThrow(
                new IllegalArgumentException("colis id ou livreur id ne peut pas être null")
        );

        // Act & Assert
        // Test with empty livreur ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> livraisonController.assignLivreurToColis(COLIS_ID, ""));

        assertTrue(exception.getMessage().contains("ne peut pas être null"));
    }

    @Test
    void assignLivreurToColis_shouldPropagateEntityNotFoundException_whenColisNotFound() {
        // Arrange
        String nonExistentColisId = "C999";
        when(livraisonService.assignLivreurToColis(eq(nonExistentColisId), eq(LIVREUR_ID))).thenThrow(
                new EntityNotFoundException("aucune colis avec cet id " + nonExistentColisId)
        );

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> livraisonController.assignLivreurToColis(nonExistentColisId, LIVREUR_ID));

        assertTrue(exception.getMessage().contains("aucune colis avec cet id"));
    }

    @Test
    void assignLivreurToColis_shouldPropagateEntityNotFoundException_whenLivreurNotFound() {
        // Arrange
        String nonExistentLivreurId = "L999";
        when(livraisonService.assignLivreurToColis(eq(COLIS_ID), eq(nonExistentLivreurId))).thenThrow(
                new EntityNotFoundException("aucun livreur avec cet id " + nonExistentLivreurId)
        );

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> livraisonController.assignLivreurToColis(COLIS_ID, nonExistentLivreurId));

        assertTrue(exception.getMessage().contains("aucun livreur avec cet id"));
    }

    @Test
    void assignLivreurToColis_shouldPropagateIllegalStateException_whenColisAlreadyAssigned() {
        // Arrange
        when(livraisonService.assignLivreurToColis(eq(COLIS_ID), eq(LIVREUR_ID))).thenThrow(
                new IllegalStateException("cete colis est déja assiggner a un livreur")
        );

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> livraisonController.assignLivreurToColis(COLIS_ID, LIVREUR_ID));

        assertTrue(exception.getMessage().contains("déja assiggner a un livreur"));
    }
}