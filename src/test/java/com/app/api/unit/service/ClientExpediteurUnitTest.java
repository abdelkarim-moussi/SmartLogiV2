package com.app.api.unit.service;

import com.app.api.dto.clientExpediteurDTO.ClientExpediteurRequestDTO;
import com.app.api.dto.clientExpediteurDTO.ClientExpediteurResponseDTO;
import com.app.api.entity.ClientExpediteur;
import com.app.api.exception.InvalidDataException;
import com.app.api.mapper.ClientExpediteurMapper;
import com.app.api.repository.ClientExpediteurRepository;
import com.app.api.service.ClientExpediteurService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientExpediteurUnitTest {

    private final String CLIENT_ID = "CLNT-001";
    private final String EMAIL = "test.client@expediteur.com";

    @Mock
    private ClientExpediteurRepository clientExpediteurRepository;

    @Mock
    private ClientExpediteurMapper clientExpediteurMapper;

    @InjectMocks
    private ClientExpediteurService clientExpediteurService;

    private ClientExpediteurRequestDTO requestDTO;
    private ClientExpediteur clientEntity;
    private ClientExpediteurResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ClientExpediteurRequestDTO.builder()
                .nom("Smith")
                .prenom("Alice")
                .email(EMAIL)
                .telephone("0700000000")
                .adresse("123 Rue de la Teste")
                .build();

        clientEntity = new ClientExpediteur();
        clientEntity.setId(CLIENT_ID);
        clientEntity.setNom("Smith");
        clientEntity.setEmail(EMAIL);
        clientEntity.setAdresse("123 Rue de la Teste");

        responseDTO = ClientExpediteurResponseDTO.builder()
                .id(CLIENT_ID)
                .nom("Smith")
                .email(EMAIL)
                .build();
    }

    // -------------------------------------------------------------------------
    // 1. getAllClients Tests
    // -------------------------------------------------------------------------

    @Test
    void getAllClients_shouldReturnPagedResults() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<ClientExpediteur> mockPage = new PageImpl<>(List.of(clientEntity), pageable, 1);

        when(clientExpediteurRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(clientExpediteurMapper.toDTO(any(ClientExpediteur.class))).thenReturn(responseDTO);

        // Act
        Page<ClientExpediteurResponseDTO> result = clientExpediteurService.getAllClients(page, size);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(CLIENT_ID, result.getContent().get(0).getId());
        verify(clientExpediteurRepository, times(1)).findAll(pageable);
    }

    // -------------------------------------------------------------------------
    // 2. createClientExpediteur Tests
    // -------------------------------------------------------------------------

    @Test
    void createClientExpediteur_shouldSucceed_whenClientDoesNotExist() {
        // Arrange
        when(clientExpediteurRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(clientExpediteurMapper.toEntity(requestDTO)).thenReturn(clientEntity);
        when(clientExpediteurRepository.save(clientEntity)).thenReturn(clientEntity);
        when(clientExpediteurMapper.toDTO(clientEntity)).thenReturn(responseDTO);

        // Act
        ClientExpediteurResponseDTO result = clientExpediteurService.createClientExpediteur(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(CLIENT_ID, result.getId());
        verify(clientExpediteurRepository, times(1)).findByEmail(EMAIL);
        verify(clientExpediteurRepository, times(1)).save(clientEntity);
    }

    @Test
    void createClientExpediteur_shouldReturnNull_whenClientAlreadyExists() {
        // Arrange
        when(clientExpediteurRepository.findByEmail(EMAIL)).thenReturn(Optional.of(clientEntity));

        // Act
        ClientExpediteurResponseDTO result = clientExpediteurService.createClientExpediteur(requestDTO);

        // Assert
        assertNull(result);
        verify(clientExpediteurRepository, times(1)).findByEmail(EMAIL);
        verify(clientExpediteurRepository, never()).save(any());
    }

    @Test
    void createClientExpediteur_withNullRequestDTO_shouldThrowInvalidDataException() {
        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> clientExpediteurService.createClientExpediteur(null));
        assertTrue(exception.getMessage().contains("ne peut être pas null"));
        verify(clientExpediteurRepository, never()).findByEmail(anyString());
    }

    // -------------------------------------------------------------------------
    // 3. getOneClient Tests
    // -------------------------------------------------------------------------

    @Test
    void getOneClient_shouldSucceed() {
        // Arrange
        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientEntity));
        when(clientExpediteurMapper.toDTO(clientEntity)).thenReturn(responseDTO);

        // Act
        ClientExpediteurResponseDTO result = clientExpediteurService.getOneClient(CLIENT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(CLIENT_ID, result.getId());
        verify(clientExpediteurRepository, times(1)).findById(CLIENT_ID);
    }

    @Test
    void getOneClient_withNullId_shouldThrowInvalidParameterException() {
        // Act & Assert
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> clientExpediteurService.getOneClient(null));
        assertTrue(exception.getMessage().contains("ne peut pas être null"));
    }

    @Test
    void getOneClient_withNonExistentId_shouldThrowEntityNotFoundException() {
        // Arrange
        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clientExpediteurService.getOneClient(CLIENT_ID));
        assertTrue(exception.getMessage().contains("aucun client exist avec cet id " + CLIENT_ID));
    }

    // -------------------------------------------------------------------------
    // 4. updateClientExpediteur Tests
    // -------------------------------------------------------------------------

    @Test
    void updateClientExpediteur_shouldSucceedAndReturnUpdatedDTO() {
        // Arrange
        ClientExpediteurRequestDTO updateDTO = ClientExpediteurRequestDTO.builder()
                .nom("UpdatedName")
                .email("new.email@exp.com")
                .build();

        ClientExpediteur savedClient = new ClientExpediteur(); // Simulate the saved result

        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientEntity));
        when(clientExpediteurRepository.save(any(ClientExpediteur.class))).thenReturn(savedClient);
        when(clientExpediteurMapper.toDTO(savedClient)).thenReturn(responseDTO); // Mock mapper for updated result

        // Act
        ClientExpediteurResponseDTO result = clientExpediteurService.updateClientExpediteur(CLIENT_ID, updateDTO);

        // Assert
        assertNotNull(result);
        // Verify entity fields were updated before saving
        assertEquals("UpdatedName", clientEntity.getNom());
        assertEquals("new.email@exp.com", clientEntity.getEmail());
        verify(clientExpediteurRepository, times(1)).save(clientEntity);
    }

    @Test
    void updateClientExpediteur_withNullId_shouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clientExpediteurService.updateClientExpediteur(null, requestDTO));
        assertTrue(exception.getMessage().contains("id ne peut pas être null"));
    }

    @Test
    void updateClientExpediteur_withNullRequestDTO_shouldThrowInvalidDataException() {
        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> clientExpediteurService.updateClientExpediteur(CLIENT_ID, null));
        assertTrue(exception.getMessage().contains("ne peut être pas null"));
    }

    @Test
    void updateClientExpediteur_withNonExistentId_shouldThrowEntityNotFoundException() {
        // Arrange
        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clientExpediteurService.updateClientExpediteur(CLIENT_ID, requestDTO));
        assertTrue(exception.getMessage().contains("aucun client avec cet id " + CLIENT_ID));
        verify(clientExpediteurRepository, never()).save(any());
    }

    // -------------------------------------------------------------------------
    // 5. deleteClientExpediteur Tests
    // -------------------------------------------------------------------------

    @Test
    void deleteClientExpediteur_shouldSucceed() {
        // Arrange
        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.of(clientEntity));
        doNothing().when(clientExpediteurRepository).deleteById(CLIENT_ID);

        // Act
        clientExpediteurService.deleteClientExpediteur(CLIENT_ID);

        // Assert
        verify(clientExpediteurRepository, times(1)).findById(CLIENT_ID);
        verify(clientExpediteurRepository, times(1)).deleteById(CLIENT_ID);
    }

    @Test
    void deleteClientExpediteur_withNullId_shouldThrowInvalidParameterException() {
        // Act & Assert
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> clientExpediteurService.deleteClientExpediteur(null));
        assertTrue(exception.getMessage().contains("id ne peut pas être null"));
        verify(clientExpediteurRepository, never()).deleteById(anyString());
    }

    @Test
    void deleteClientExpediteur_withNonExistentId_shouldThrowEntityNotFoundException() {
        // Arrange
        when(clientExpediteurRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> clientExpediteurService.deleteClientExpediteur(CLIENT_ID));
        assertTrue(exception.getMessage().contains("aucun client avec cet id " + CLIENT_ID));
        verify(clientExpediteurRepository, never()).deleteById(anyString());
    }
}