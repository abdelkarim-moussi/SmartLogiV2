package com.app.api.unit.service;

import com.app.api.dto.livreurDTO.LivreurRequestDTO;
import com.app.api.dto.livreurDTO.LivreurResponseDTO;
import com.app.api.entity.Livreur;
import com.app.api.entity.User;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.NotFoundException;
import com.app.api.mapper.LivreurMapper;
import com.app.api.repository.LivreurRepository;
import com.app.api.repository.UserRepository;
import com.app.api.security.service.UserService;
import com.app.api.service.LivreurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreurServiceUnitTest {

    private final String LIVREUR_ID = "LIVR-001";
    private final String EMAIL = "jean.dupont@delivery.com";

    @Mock
    private LivreurMapper livreurMapper;

    @Mock
    private LivreurRepository livreurRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private LivreurService livreurService;


    private LivreurRequestDTO requestDTO;
    private Livreur livreurEntity;
    private LivreurResponseDTO responseDTO;
    private User mockUser;

    @BeforeEach
    void setUp() {
        requestDTO = LivreurRequestDTO.builder()
                .nom("Dupont")
                .prenom("Jean")
                .email(EMAIL)
                .telephone("0612345678")
                .vehicule("Moto")
                .build();

        livreurEntity = new Livreur();
        livreurEntity.setId(LIVREUR_ID);
        livreurEntity.setNom("Dupont");
        livreurEntity.setVehicule("Moto");

        mockUser = new User();
        mockUser.setId("U001");


        responseDTO = LivreurResponseDTO.builder()
                .id(LIVREUR_ID)
                .nom("Dupont")
                .build();
    }

    // --- 1. createLivreur Tests ---

    @Test
    void createLivreur_shouldSucceedAndReturnResponseDTO() {
        // Arrange
        when(userRepository.existsByEmail(requestDTO.getNonUtilisateur())).thenReturn(false);
        when(userService.addUserHelper(eq(requestDTO.getNonUtilisateur()), eq(requestDTO.getPassword()), anySet()))
                .thenReturn(mockUser);
        when(livreurMapper.toEntity(requestDTO)).thenReturn(livreurEntity);
        when(livreurRepository.save(any(Livreur.class))).thenReturn(livreurEntity);
        when(livreurMapper.toDTO(livreurEntity)).thenReturn(responseDTO);

        // Act
        LivreurResponseDTO result = livreurService.createLivreur(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("LIVR-001", result.getId());
        verify(userRepository).existsByEmail(requestDTO.getNonUtilisateur());
        verify(livreurRepository).save(livreurEntity);
        // Verify that the user was set on the entity
        assertEquals(mockUser, livreurEntity.getUser());
    }

    @Test
    void createLivreur_withNullRequestDTO_shouldThrowInvalidDataException() {
        // Act & Assert
        assertThrows(InvalidDataException.class, () -> livreurService.createLivreur(null));
        verify(livreurRepository, never()).save(any());
    }

    // --- 2. deleteLivreur Tests ---

    @Test
    void deleteLivreur_shouldSucceed() {
        // Arrange
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(new Livreur()));
        doNothing().when(livreurRepository).deleteById(LIVREUR_ID);

        // Act
        livreurService.deleteLivreur(LIVREUR_ID);

        // Assert
        verify(livreurRepository, times(1)).deleteById(LIVREUR_ID);
    }

    @Test
    void deleteLivreur_withNullId_shouldThrowInvalidDataException() {
        // Act & Assert
        assertThrows(InvalidDataException.class, () -> livreurService.deleteLivreur(null));
        verify(livreurRepository, never()).deleteById(anyString());
    }

    // --- 3. updateLivreur Tests ---

    @Test
    void updateLivreur_shouldSucceedAndReturnUpdatedDTO() {
        // Arrange
        LivreurRequestDTO updateDTO = LivreurRequestDTO.builder()
                .nom("UpdatedNom")
                .email("new@email.com")
                .build();

        Livreur updatedEntity = new Livreur();
        updatedEntity.setNom("UpdatedNom");

        LivreurResponseDTO updatedResponseDTO = LivreurResponseDTO.builder().nom("UpdatedNom").build();

        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));
        when(livreurRepository.save(any(Livreur.class))).thenReturn(updatedEntity);
        when(livreurMapper.toDTO(any(Livreur.class))).thenReturn(updatedResponseDTO);

        // Act
        LivreurResponseDTO result = livreurService.updateLivreur(LIVREUR_ID, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedNom", result.getNom());
        verify(livreurRepository, times(1)).findById(LIVREUR_ID);
        verify(livreurRepository, times(1)).save(livreurEntity);
        assertEquals("UpdatedNom", livreurEntity.getNom()); // Verify entity modification
        assertEquals("new@email.com", livreurEntity.getEmail());
    }

    @Test
    void updateLivreur_withNonExistentId_shouldThrowResourceNotFoundException() {
        // Arrange
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class,
                () -> livreurService.updateLivreur(LIVREUR_ID, requestDTO));
        verify(livreurRepository, never()).save(any());
    }

    @Test
    void updateLivreur_withNullId_shouldThrowInvalidDataException() {
        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> livreurService.updateLivreur(null, requestDTO));
    }

    @Test
    void updateLivreur_withNullRequestDTO_shouldThrowInvalidDataException() {
        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> livreurService.updateLivreur(LIVREUR_ID, null));
    }

    // --- 4. getOneLivreur Tests ---

    @Test
    void getOneLivreur_shouldSucceedAndReturnDTO() {
        // Arrange
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.of(livreurEntity));
        when(livreurMapper.toDTO(livreurEntity)).thenReturn(responseDTO);

        // Act
        LivreurResponseDTO result = livreurService.getOneLivreur(LIVREUR_ID);

        // Assert
        assertNotNull(result);
        assertEquals(LIVREUR_ID, result.getId());
        verify(livreurRepository, times(1)).findById(LIVREUR_ID);
    }

    @Test
    void getOneLivreur_withNullId_shouldThrowInvalidDataException() {
        // Act & Assert
        assertThrows(InvalidDataException.class, () -> livreurService.getOneLivreur(null));
    }

    @Test
    void getOneLivreur_withNonExistentId_shouldThrowResourceNotFoundException() {
        // Arrange
        when(livreurRepository.findById(LIVREUR_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> livreurService.getOneLivreur(LIVREUR_ID));
    }

    // --- 5. getAllLivreur Tests ---

    @Test
    void getAllLivreur_shouldSucceedAndReturnList() {
        // Arrange
        List<Livreur> livreurList = List.of(livreurEntity);
        when(livreurRepository.findAll()).thenReturn(livreurList);
        when(livreurMapper.toDTO(any(Livreur.class))).thenReturn(responseDTO);

        // Act
        List<LivreurResponseDTO> result = livreurService.getAllLivreur();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(livreurRepository, times(1)).findAll();
        verify(livreurMapper, times(1)).toDTO(livreurEntity);
    }

    @Test
    void getAllLivreur_withEmptyList_shouldThrowResourceNotFoundException() {
        // Arrange
        when(livreurRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> livreurService.getAllLivreur());
        verify(livreurRepository, times(1)).findAll();
        verify(livreurMapper, never()).toDTO(any());
    }
}