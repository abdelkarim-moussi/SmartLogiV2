package com.app.api.unit.service;

import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import com.app.api.mapper.ColisMapper;
import com.app.api.mapper.LivreurMapper;
import com.app.api.repository.ColisRepository;
import com.app.api.repository.LivreurRepository;
import com.app.api.service.LivraisonService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivraisonServiceUnitTest {

    private final String COLIS_ID = "C001";
    private final String LIVREUR_ID = "L001";
    private final String DIFFERENT_LIVREUR_ID = "L002";

    @Mock
    private ColisRepository colisRepository;
    @Mock
    private LivreurRepository livreurRepository;
    @Mock
    private ColisMapper colisMapper;
    @Mock
    private LivreurMapper livreurMapper;

    @InjectMocks
    private LivraisonService livraisonService;

    private Colis colisEntity;
    private Livreur livreurEntity;
    private Livreur differentLivreurEntity;
    private ColisResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // Unassigned Colis entity (assuming Colis has a no-args constructor or builder)
        colisEntity = Colis.builder().id(COLIS_ID).livreur(null).build();

        // Livreur entity to be assigned
        livreurEntity = new Livreur();
        livreurEntity.setId(LIVREUR_ID);
        livreurEntity.setNom("Livreur A");

        // Different Livreur for reassignment test
        differentLivreurEntity = new Livreur();
        differentLivreurEntity.setId(DIFFERENT_LIVREUR_ID);
        differentLivreurEntity.setNom("Livreur B");

        // Expected DTO response
        responseDTO = ColisResponseDTO.builder().id(COLIS_ID).build();
    }

    // --- Success Case ---

    @Test
    void assignLivreurToColis_shouldAssignLivreur_whenColisIsUnassigned() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));

        // Mock the save result
        Colis updatedColis = Colis.builder().id(COLIS_ID).livreur(livreurEntity).build();
        when(colisRepository.save(any(Colis.class))).thenReturn(updatedColis);
        when(colisMapper.toDTO(updatedColis)).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID);

        // Assert
        assertNotNull(result);
        // Verify entity was updated and saved
        assertEquals(livreurEntity, colisEntity.getLivreur());
        verify(colisRepository, times(1)).save(colisEntity);
        verify(colisMapper, times(1)).toDTO(updatedColis);
    }

    // --- Input Validation Failures ---

    @Test
    void assignLivreurToColis_withNullColisId_shouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> livraisonService.assignLivreurToColis(null, LIVREUR_ID));
        assertTrue(exception.getMessage().contains("ne peut pas être null"));
        verify(colisRepository, never()).findById(anyString());
    }

    @Test
    void assignLivreurToColis_withEmptyLivreurId_shouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> livraisonService.assignLivreurToColis(COLIS_ID, ""));
        assertTrue(exception.getMessage().contains("ne peut pas être null"));
        verify(colisRepository, never()).findById(anyString());
    }

    // --- Resource Not Found Failures ---

    @Test
    void assignLivreurToColis_whenColisNotFound_shouldThrowEntityNotFoundException() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID));
        assertTrue(exception.getMessage().contains("aucune colis avec cet id " + COLIS_ID));
        verify(livreurRepository, never()).findById(anyString());
        verify(colisRepository, never()).save(any());
    }

    @Test
    void assignLivreurToColis_whenLivreurNotFound_shouldThrowEntityNotFoundException() {
        // Arrange
        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID));
        assertTrue(exception.getMessage().contains("aucun livreur avec cet id " + LIVREUR_ID));
        verify(colisRepository, never()).save(any());
    }

    // --- Business Logic Failures ---

    @Test
    void assignLivreurToColis_whenAlreadyAssignedToSameLivreur_shouldReturnExistingDTOWithoutSaving() {
        // Arrange
        colisEntity.setLivreur(livreurEntity); // Colis is already assigned to Livreur L001

        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));
        when(colisMapper.toDTO(colisEntity)).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID);

        // Assert
        assertNotNull(result);
        verify(colisRepository, never()).save(any()); // Should not save
        verify(colisMapper, times(1)).toDTO(colisEntity); // Should return existing DTO
    }

    @Test
    void assignLivreurToColis_whenAlreadyAssignedToDifferentLivreur_shouldThrowIllegalStateException() {
        // Arrange
        colisEntity.setLivreur(differentLivreurEntity); // Colis assigned to L002

        when(colisRepository.findById(COLIS_ID)).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity)); // Trying to assign L001

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> livraisonService.assignLivreurToColis(COLIS_ID, LIVREUR_ID));
        assertTrue(exception.getMessage().contains("déja assiggner a un livreur"));
        verify(colisRepository, never()).save(any());
    }
}