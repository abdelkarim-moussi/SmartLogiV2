package com.app.api.unit.controller;

import com.app.api.controller.ColisController;
import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.enums.ColisStatus;
import com.app.api.service.ColisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColisControllerUnitTest {

    private final String COLIS_ID = "C001";
    private final String LIVREUR_ID = "L001";

    @Mock
    private ColisService colisService;

    @InjectMocks
    private ColisController colisController;

    private ColisRequestDTO requestDTO;
    private ColisResponseDTO responseDTO;
    private Page<ColisResponseDTO> colisPage;

    @BeforeEach
    void setUp() {
        requestDTO = ColisRequestDTO.builder()
                .description("Test Package")
                .poids(1.0F)
                .build();

        responseDTO = ColisResponseDTO.builder()
                .id(COLIS_ID)
                .status(ColisStatus.creer)
                .description("Test Package")
                .build();

        colisPage = new PageImpl<>(Collections.singletonList(responseDTO));
    }

    // --- 1. getAllColis Tests ---

    @Test
    void getAllColis_shouldCallServiceAndReturnPage() {
        // Arrange
        when(colisService.getAllColis(any(ColisFilterDTO.class), eq(0), eq(5), eq("status"), eq("DESC")))
                .thenReturn(colisPage);

        // Act
        ResponseEntity<Page<ColisResponseDTO>> response = colisController.getAllColis(
                0, 5, "status", "DESC", "creer", "express", "Casa", "ZoneA", "search"
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());

        // Verify argument mapping to ColisFilterDTO
        ArgumentCaptor<ColisFilterDTO> filterCaptor = ArgumentCaptor.forClass(ColisFilterDTO.class);
        verify(colisService).getAllColis(filterCaptor.capture(), eq(0), eq(5), eq("status"), eq("DESC"));

        ColisFilterDTO capturedFilter = filterCaptor.getValue();
        assertEquals("creer", capturedFilter.getStatus());
        assertEquals("Casa", capturedFilter.getVille());
    }

    // --- 2. getOneColis Tests ---

    @Test
    void getOneColis_shouldCallServiceAndReturnDTO() {
        // Arrange
        when(colisService.getColisById(COLIS_ID)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ColisResponseDTO> response = colisController.getOneColis(COLIS_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COLIS_ID, response.getBody().getId());
        verify(colisService, times(1)).getColisById(COLIS_ID);
    }

    // --- 3. createColis Tests ---

    @Test
    void createColis_shouldCallServiceAndReturnCreatedDTO() {
        // Arrange
        when(colisService.createColis(requestDTO)).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = colisController.createColis(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(COLIS_ID, result.getId());
        verify(colisService, times(1)).createColis(requestDTO);
    }

    // --- 4. updateColis Tests ---

    @Test
    void updateColis_shouldCallServiceAndReturnUpdatedDTO() {
        // Arrange
        ColisRequestDTO updateDTO = ColisRequestDTO.builder().description("New Desc").build();
        when(colisService.updateColis(COLIS_ID, updateDTO)).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = colisController.updateColis(COLIS_ID, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(COLIS_ID, result.getId());
        verify(colisService, times(1)).updateColis(COLIS_ID, updateDTO);
    }

    // --- 5. deleteColis Tests ---

    @Test
    void deleteColis_shouldCallServiceAndReturnNoContent() {
        // Arrange
        doNothing().when(colisService).deleteColis(COLIS_ID);

        // Act
        ResponseEntity<Void> response = colisController.deleteColis(COLIS_ID);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(colisService, times(1)).deleteColis(COLIS_ID);
    }

    // --- 6. updateColisStatus Tests ---

    @Test
    void updateColisStatus_shouldCallServiceWithCorrectStatusEnum() {
        // Arrange
        String statusString = "livrer";
        ColisStatus expectedStatus = ColisStatus.valueOf(statusString);
        when(colisService.updateColisStatus(COLIS_ID, expectedStatus)).thenReturn(responseDTO);

        // Act
        ColisResponseDTO result = colisController.updateColisStatus(COLIS_ID, statusString);

        // Assert
        assertNotNull(result);
        assertEquals(COLIS_ID, result.getId());
        verify(colisService, times(1)).updateColisStatus(COLIS_ID, expectedStatus);
    }
}