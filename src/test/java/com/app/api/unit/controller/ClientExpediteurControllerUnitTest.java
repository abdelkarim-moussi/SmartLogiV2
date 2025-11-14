package com.app.api.unit.controller;

import com.app.api.controller.ClientExpediteurController;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.exception.InvalidDataException;
import com.app.api.service.ClientExpediteurService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.InvalidParameterException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientExpediteurControllerUnitTest {

    private final String CLIENT_ID = "CLNT-001";
    private final String NON_EXISTENT_ID = "CLNT-999";

    @Mock
    private ClientExpediteurService clientExpediteurService;

    @InjectMocks
    private ClientExpediteurController clientExpediteurController;

    private ClientExpediteurRequestDTO requestDTO;
    private ClientExpediteurResponseDTO responseDTO;
    private Page<ClientExpediteurResponseDTO> clientPage;

    @BeforeEach
    void setUp() {
        requestDTO = ClientExpediteurRequestDTO.builder()
                .nom("Smith")
                .email("smith@test.com")
                .build();

        responseDTO = ClientExpediteurResponseDTO.builder()
                .id(CLIENT_ID)
                .nom("Smith")
                .build();

        clientPage = new PageImpl<>(Collections.singletonList(responseDTO));
    }

    // --- 1. getAllClientExpediteur Tests ---

    @Test
    void getAllClientExpediteur_shouldCallServiceAndReturnPage() {
        // Arrange
        int page = 0;
        int size = 5;
        when(clientExpediteurService.getAllClients(page, size)).thenReturn(clientPage);

        // Act
        ResponseEntity<Page<ClientExpediteurResponseDTO>> response = clientExpediteurController.getAllClientExpediteur(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        verify(clientExpediteurService, times(1)).getAllClients(page, size);
    }

    // --- 2. getClientExpediteur Tests ---

    @Test
    void getClientExpediteur_shouldCallServiceAndReturnDTO() {
        // Arrange
        when(clientExpediteurService.getOneClient(CLIENT_ID)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClientExpediteurResponseDTO> response = clientExpediteurController.getClientExpediteur(CLIENT_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CLIENT_ID, response.getBody().getId());
        verify(clientExpediteurService, times(1)).getOneClient(CLIENT_ID);
    }

    @Test
    void getClientExpediteur_shouldPropagateInvalidParameterException() {
        // Arrange
        when(clientExpediteurService.getOneClient(NON_EXISTENT_ID))
                .thenThrow(new InvalidParameterException("id ne peut pas être null"));

        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientExpediteurController.getClientExpediteur(NON_EXISTENT_ID));
    }

    @Test
    void getClientExpediteur_shouldPropagateEntityNotFoundException() {
        // Arrange
        when(clientExpediteurService.getOneClient(NON_EXISTENT_ID))
                .thenThrow(new EntityNotFoundException("aucun client exist avec cet id " + NON_EXISTENT_ID));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> clientExpediteurController.getClientExpediteur(NON_EXISTENT_ID));
    }

    // --- 3. createClientExpediteur Tests ---

    @Test
    void createClientExpediteur_shouldCallServiceAndReturnCreatedDTO() {
        // Arrange
        when(clientExpediteurService.createClientExpediteur(requestDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClientExpediteurResponseDTO> response = clientExpediteurController.createClientExpediteur(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CLIENT_ID, response.getBody().getId());
        verify(clientExpediteurService, times(1)).createClientExpediteur(requestDTO);
    }

    @Test
    void createClientExpediteur_shouldPropagateInvalidDataException() {
        // Arrange
        when(clientExpediteurService.createClientExpediteur(any()))
                .thenThrow(new InvalidDataException("les données de création ne peut être pas null"));

        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> clientExpediteurController.createClientExpediteur(requestDTO));
    }

    // --- 4. updateClientExpediteur Tests ---

    @Test
    void updateClientExpediteur_shouldCallServiceAndReturnUpdatedDTO() {
        // Arrange
        ClientExpediteurRequestDTO updateDTO = ClientExpediteurRequestDTO.builder().nom("Updated").build();
        when(clientExpediteurService.updateClientExpediteur(CLIENT_ID, updateDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClientExpediteurResponseDTO> response = clientExpediteurController.updateClientExpediteur(CLIENT_ID, updateDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CLIENT_ID, response.getBody().getId());
        verify(clientExpediteurService, times(1)).updateClientExpediteur(CLIENT_ID, updateDTO);
    }

    @Test
    void updateClientExpediteur_shouldPropagateIllegalArgumentException() {
        // Arrange
        when(clientExpediteurService.updateClientExpediteur(eq(CLIENT_ID), any()))
                .thenThrow(new IllegalArgumentException("client id ne peut pas être null"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> clientExpediteurController.updateClientExpediteur(CLIENT_ID, requestDTO));
    }

    @Test
    void updateClientExpediteur_shouldPropagateEntityNotFoundException() {
        // Arrange
        when(clientExpediteurService.updateClientExpediteur(eq(NON_EXISTENT_ID), any()))
                .thenThrow(new EntityNotFoundException("aucun client avec cet id " + NON_EXISTENT_ID));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> clientExpediteurController.updateClientExpediteur(NON_EXISTENT_ID, requestDTO));
    }

    // --- 5. deleteClientExpediteur Tests ---

    @Test
    void deleteClientExpediteur_shouldCallServiceAndReturnSuccessMessage() {
        // Arrange
        doNothing().when(clientExpediteurService).deleteClientExpediteur(CLIENT_ID);

        // Act
        ResponseEntity<String> response = clientExpediteurController.deleteClientExpediteur(CLIENT_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("supprimer avec succés"));
        verify(clientExpediteurService, times(1)).deleteClientExpediteur(CLIENT_ID);
    }

    @Test
    void deleteClientExpediteur_shouldPropagateInvalidParameterException() {
        // Arrange
        doThrow(new InvalidParameterException("id ne peut pas être null"))
                .when(clientExpediteurService).deleteClientExpediteur(any());

        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientExpediteurController.deleteClientExpediteur(CLIENT_ID));
    }
}