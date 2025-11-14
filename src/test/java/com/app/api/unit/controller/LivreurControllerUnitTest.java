package com.app.api.unit.controller;

import com.app.api.controller.LivreurController;
import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.service.LivreurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreurControllerUnitTest {

    private final String LIVREUR_ID = "L001";
    private final String NON_EXISTENT_ID = "L999";

    @Mock
    private LivreurService livreurService;

    @InjectMocks
    private LivreurController livreurController;

    private LivreurRequestDTO requestDTO;
    private LivreurResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = LivreurRequestDTO.builder()
                .nom("Dupont")
                .prenom("Jean")
                .email("jean.dupont@test.com")
                .build();

        responseDTO = LivreurResponseDTO.builder()
                .id(LIVREUR_ID)
                .nom("Dupont")
                .build();
    }

    // --- 1. getAllLivreur Tests ---

    @Test
    void getAllLivreur_shouldReturnListOfLivreurs() {
        // Arrange
        List<LivreurResponseDTO> expectedList = Collections.singletonList(responseDTO);
        when(livreurService.getAllLivreur()).thenReturn(expectedList);

        // Act
        List<LivreurResponseDTO> result = livreurController.getAllLivreur();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(LIVREUR_ID, result.get(0).getId());
        verify(livreurService, times(1)).getAllLivreur();
    }

    // --- 2. getOneLivreur Tests ---

    @Test
    void getOneLivreur_shouldReturnLivreurDTO() {
        // Arrange
        when(livreurService.getOneLivreur(LIVREUR_ID)).thenReturn(responseDTO);

        // Act
        LivreurResponseDTO result = livreurController.getOneLivreur(LIVREUR_ID);

        // Assert
        assertNotNull(result);
        assertEquals(LIVREUR_ID, result.getId());
        verify(livreurService, times(1)).getOneLivreur(LIVREUR_ID);
    }

    // --- 3. createLivreur Tests ---

    @Test
    void createLivreur_shouldCallServiceAndReturnCreatedDTO() {
        // Arrange
        when(livreurService.createLivreur(requestDTO)).thenReturn(responseDTO);

        // Act
        LivreurResponseDTO result = livreurController.createLivreur(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(LIVREUR_ID, result.getId());
        verify(livreurService, times(1)).createLivreur(requestDTO);
    }

    // --- 4. updateLivreur Tests ---

    @Test
    void updateLivreur_shouldCallServiceAndReturnUpdatedDTO() {
        // Arrange
        LivreurRequestDTO updateDTO = LivreurRequestDTO.builder().nom("New Name").build();
        when(livreurService.updateLivreur(LIVREUR_ID, updateDTO)).thenReturn(responseDTO);

        // Act
        LivreurResponseDTO result = livreurController.updateLivreur(LIVREUR_ID, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(LIVREUR_ID, result.getId());
        verify(livreurService, times(1)).updateLivreur(LIVREUR_ID, updateDTO);
    }

    // --- 5. deleteLivreur Tests ---

    @Test
    void deleteLivreur_shouldCallServiceAndReturnSuccessMessage() {
        // Arrange
        doNothing().when(livreurService).deleteLivreur(LIVREUR_ID);

        // Act
        String result = livreurController.deleteLivreur(LIVREUR_ID);

        // Assert
        assertTrue(result.contains("supprimer avec succes"));
        verify(livreurService, times(1)).deleteLivreur(LIVREUR_ID);
    }

    @Test
    void deleteLivreur_shouldPropagateResourceNotFoundException() {
        // Arrange
        doThrow(new ResourceNotFoundException("aucun livreur avec cet id")).when(livreurService).deleteLivreur(NON_EXISTENT_ID);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> livreurController.deleteLivreur(NON_EXISTENT_ID));

        // Verify the error message propagated
        assertTrue(exception.getMessage().contains("aucun livreur avec cet id"));
        verify(livreurService, times(1)).deleteLivreur(NON_EXISTENT_ID);
    }
}