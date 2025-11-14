package com.app.api.unit.service;

import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.*;
import com.app.api.repository.*;
import com.app.api.service.ColisService;
import io.micrometer.core.instrument.config.validate.Validated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColisServiceUnitTest {

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
    private Colis colisEntity;
    private ColisResponseDTO colisResponseDTO;
    private String colisId;
    private Pageable pageable;
    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

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

        colisEntity = new Colis();
        colisEntity.setId(colisId);
        colisEntity.setPoids(2.5F);
        colisEntity.setDescription("Package contenant des documents");
        colisEntity.setPriority(ColisPriority.express);
        colisEntity.setStatus(ColisStatus.creer);
        colisEntity.setVilleDestination("Casablanca");

        colisResponseDTO = ColisResponseDTO.builder()
                .id(colisId)
                .poids(2.5F)
                .description("Package contenant des documents")
                .priority(ColisPriority.express)
                .status(ColisStatus.creer)
                .villeDestination("Casablanca")
                .build();

        pageable = PageRequest.of(0,5,Sort.by("status").ascending());

    }

    @Test
    void createColis_WithValidData_ShouldReturnColisResponseDTO() {

        //Arrange
        when(colisMapper.toEntity(colisRequestDTO)).thenReturn(colisEntity);
        when(colisRepository.save(any(Colis.class))).thenReturn(colisEntity);
        when(colisMapper.toDTO(colisEntity)).thenReturn(colisResponseDTO);

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

    @Test
    void createColis_WithNullRequest_ThrowsInvalidDataException(){

        InvalidDataException exception = assertThrows(InvalidDataException.class,()->colisService.createColis(null));
        assertEquals("données invalide",exception.getMessage());

    }

    @Test
    void updateColis_WithValidData_ShouldReturnUpdatedColis(){
        when(colisRepository.findById(colisId)).thenReturn(Optional.of(colisEntity));
        when(colisRepository.save(colisEntity)).thenReturn(colisEntity);
        when(colisMapper.toDTO(colisEntity)).thenReturn(colisResponseDTO);

        ClientExpediteur expediteur = new ClientExpediteur();
        expediteur.setId("EXP123");
        when(clientExpediteurRepository.findById("EXP123")).thenReturn(Optional.of(expediteur));

        Zone zone = new Zone();
        zone.setId("zone1");
        zone.setCodePostal("45000");
        when(zoneRepository.findByCodePostal(zone.getCodePostal())).thenReturn(Optional.of(zone));

        ColisResponseDTO result = colisService.updateColis(colisId,colisRequestDTO);

        assertNotNull(result);
        verify(colisRepository).findById(colisId);
        verify(colisRepository).save(colisEntity);
        verify(colisMapper).toDTO(colisEntity);

    }

    @Test
    void  updateColis_WithNullId_ShouldThrowException(){
        InvalidDataException exception = assertThrows(InvalidDataException.class,() -> colisService.updateColis(null,colisRequestDTO));

        assertEquals("invalid id",exception.getMessage());
    }

    @Test
    void updateColis_WithNullData_ShouldThrowException(){

        InvalidDataException exception = assertThrows(InvalidDataException.class,() -> colisService.updateColis(colisId,null));
        assertEquals("données invalide",exception.getMessage());

    }

    @Test
    void updateColis_WithNullData_ShouldThrowResourceNotFoundException(){

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,() -> colisService.updateColis(colisId,colisRequestDTO));
        assertEquals("aucune colis trouver avec id : "+colisId,exception.getMessage());

    }

    @Test
    void getAllColis_WithPagination_ShouldReturn_CorrectPageWith(){
        //Arrange
        List<Colis> colisEntities = List.of(new Colis(),new Colis(),new Colis());
        Page<Colis> mockPage = new PageImpl<>(colisEntities,pageable,colisEntities.size());
        when(colisRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(mockPage);
        when(colisMapper.toDTO(any(Colis.class))).thenReturn(colisResponseDTO);

        //Act

        Page<ColisResponseDTO> page = colisService.getAllColis(
                new ColisFilterDTO(),0,5,"status","ASC"
        );

        //Assert
        assertNotNull(page);
        assertNotNull(page.getPageable());
        assertEquals(3,page.getTotalElements());
        assertEquals(5,page.getSize());
        assertEquals(0,page.getNumber());
    }

    @Test
    void getAllColis_WithInvalidSort_ShouldReturn_CorrectPageWithByDefaultSort(){
        List<Colis> colisEntities = List.of(new Colis(),new Colis(),new Colis());
        String invalidSort = "invalidSort";
        Pageable pageable1 = PageRequest.of(0,5,Sort.by(invalidSort).ascending());

        Page<Colis> mockPage = new PageImpl<>(colisEntities,pageable1,colisEntities.size());

        when(colisRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenAnswer(invocation -> {
                        Pageable pageable = invocation.getArgument(1);
                        assertTrue(pageable.getSort().getOrderFor("status") != null);
                        return mockPage;
                        }
                        )
                .thenReturn(mockPage);
        when(colisMapper.toDTO(any(Colis.class))).thenReturn(colisResponseDTO);

        //Act

        Page<ColisResponseDTO> page = colisService.getAllColis(
                new ColisFilterDTO(),0,5,invalidSort,"ASC"
        );

        //Assert
        assertNotNull(page);
        verify(colisRepository).findAll(any(Specification.class),any(Pageable.class));
    }

    @Test
    void getAllColis_WithDescendingSort_ShouldApplyDescendingSort() {
        // Arrange
        String sortBy = "priority";
        String sortDir = "desc"; // Should create DESCENDING sort

        Page<Colis> colisPage = new PageImpl<>(List.of(colisEntity));
        when(colisRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(colisPage);
        when(colisMapper.toDTO(colisEntity)).thenReturn(colisResponseDTO);

        // Act
        colisService.getAllColis(new ColisFilterDTO(), 0, 10, sortBy, sortDir);

        // Assert
        verify(colisRepository).findAll(any(Specification.class), pageableCaptor.capture());

        Pageable usedPageable = pageableCaptor.getValue();
        Sort.Order sortOrder = usedPageable.getSort().getOrderFor("priority");

        assertNotNull(sortOrder);
        assertEquals(Sort.Direction.DESC, sortOrder.getDirection());
    }

    @Test
    void getOneColis_WithValidId_ShouldReturnCorrectResult(){
        when(colisRepository.findById(colisId)).thenReturn(Optional.of(colisEntity));
        when(colisMapper.toDTO(colisEntity)).thenReturn(colisResponseDTO);

        ColisResponseDTO result = colisService.getOneColisById(colisId);

        assertNotNull(result);
        assertEquals(colisId,result.getId());
        verify(colisRepository,times(1)).findById(colisId);
    }

    @Test
    void getOneColis_WithNullId_ShouldThrowException(){
        InvalidDataException exception = assertThrows(InvalidDataException.class,() -> colisService.getOneColisById(null));

        assertEquals("invalide id",exception.getMessage());

    }

    @Test
    void getOneColis_WithInvalidId_ShouldThrowException(){
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()-> colisService.getOneColisById("COLIS_ID"));

        assertEquals("aucune colis disponible avec cet id : COLIS_ID",exception.getMessage());
    }

}