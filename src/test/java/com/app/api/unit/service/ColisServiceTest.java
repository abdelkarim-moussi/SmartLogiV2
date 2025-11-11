package com.app.api.unit.service;

import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonRequestDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.*;
import com.app.api.repository.*;
import com.app.api.service.ColisService;
import com.app.api.specification.ColisSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColisServiceTest {

    @Mock
    private ColisRepository colisRepository;

    @Mock
    private ColisMapper colisMapper;

    @Mock
    private LivreurRepository livreurRepository;

    @Mock
    private ClientExpediteurRepository clientExpediteurRepository;

    @Mock
    private DestinataireRepository destinataireRepository;

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Mock
    private DestinataireMapper destinataireMapper;

    @Mock
    private ZoneMapper zoneMapper;

    @Mock
    private ColisProduitRepository colisProduitRepository;

    @Mock
    private HistoriqueLivraisonRepository historiqueLivraisonRepository;

    @Mock
    private HistoriqueLivraisonMapper historiqueLivraisonMapper;

    @InjectMocks
    private ColisService colisService;

    private ColisRequestDTO colisRequestDTO;
    private Colis colis;
    private ColisResponseDTO colisResponseDTO;
    private String colisId;

    @BeforeEach
    void setUp() {
        colisId = "COLIS-123";

        colisRequestDTO = ColisRequestDTO.builder()
                .poids(2.5F)
                .description("Package contenant des documents")
                .priority(ColisPriority.express)
                .status(ColisStatus.creer)
                .villeDestination("Casablanca")
                .clientExpediteurId("EXP123")
                .codePostal("45000")
                .build();

        colis = new Colis();
        colis.setId(colisId);
        colis.setPoids(2.5F);
        colis.setDescription("Package contenant des documents");
        colis.setPriority(ColisPriority.express);
        colis.setStatus(ColisStatus.creer);
        colis.setVilleDestination("Casablanca");

        colisResponseDTO = ColisResponseDTO.builder()
                .id(colisId)
                .poids(2.5F)
                .description("Package contenant des documents")
                .priority(ColisPriority.express)
                .status(ColisStatus.creer)
                .villeDestination("Casablanca")
                .build();
    }


    @Test
    void createColis_WithValidData_ShouldReturnColisResponseDTO() {

        //Arrange
        when(colisMapper.toEntity(colisRequestDTO)).thenReturn(colis);
        when(colisRepository.save(any(Colis.class))).thenReturn(colis);
        when(colisMapper.toDTO(colis)).thenReturn(colisResponseDTO);

        //Mock Relations
        ClientExpediteur expediteur = new ClientExpediteur();
        expediteur.setId("EXP123");
        when(clientExpediteurRepository.findById("EXP123")).thenReturn(Optional.of(expediteur));

        Zone zone = new Zone();
        zone.setId("zone1");
        zone.setCodePostal("45000");
        when(zoneRepository.findByCodePostal(zone.getCodePostal())).thenReturn(Optional.of(zone));

        //Act

        ColisResponseDTO result = colisService.createColis(colisRequestDTO);

        //Assert

        assertNotNull(result);
        assertEquals(result.getId(),colisId);
        verify(colisRepository,times(2)).save(any(Colis.class));

    }

}