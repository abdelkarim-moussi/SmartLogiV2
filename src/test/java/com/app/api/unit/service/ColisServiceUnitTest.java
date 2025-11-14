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
import jakarta.persistence.EntityNotFoundException;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        colisEntity.setHistoriqueLivraison(new HashSet<>());

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
    void updateColis_WithNullId_ShouldThrowException(){
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
        // Mocking findById to return empty optional for this test
        when(colisRepository.findById(colisId)).thenReturn(Optional.empty());

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
        // Note: The service logic overrides invalidSort, so the actual mockPageable may not reflect it.
        Pageable pageable1 = PageRequest.of(0,5,Sort.by("status").ascending());


        Page<Colis> mockPage = new PageImpl<>(colisEntities,pageable1,colisEntities.size());

        when(colisRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenAnswer(invocation -> {
                            Pageable pageable = invocation.getArgument(1);
                            // Check if the sort field was correctly set to the default "status"
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
    void getOneColisById_WithValidId_ShouldReturnCorrectResult(){
        when(colisRepository.findById(colisId)).thenReturn(Optional.of(colisEntity));
        when(colisMapper.toDTO(colisEntity)).thenReturn(colisResponseDTO);

        ColisResponseDTO result = colisService.getColisById(colisId);

        assertNotNull(result);
        assertEquals(colisId,result.getId());
        verify(colisRepository,times(1)).findById(colisId);
    }

    @Test
    void getOneColisById_WithNullId_ShouldThrowException(){
        InvalidDataException exception = assertThrows(InvalidDataException.class,() -> colisService.getColisById(null));

        assertEquals("invalide id",exception.getMessage());

    }

    @Test
    void getOneColisById_WithInvalidId_ShouldThrowException(){
        when(colisRepository.findById(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()-> colisService.getColisById("COLIS_ID"));
        assertEquals("Colis not found: COLIS_ID",exception.getMessage());
    }

    @Test
    void deleteColis_WithValidId_ShouldDeleteTheColis(){
        when(colisRepository.findById(colisId)).thenReturn(Optional.of(colisEntity));
        doNothing().when(colisRepository).deleteById(colisId);

        colisService.deleteColis(colisId);

        verify(colisRepository,times(1)).deleteById(colisId);
        verify(colisRepository,times(1)).findById(colisId);
    }

    @Test
    void deleteColis_WithNullId_ShouldThrowException(){
        InvalidDataException exception = assertThrows(InvalidDataException.class,() -> colisService.deleteColis(null));

        assertEquals("invalide id",exception.getMessage());
    }

    @Test
    void deleteColis_WithInvalid_ShouldThrowException(){
        when(colisRepository.findById(anyString())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()-> colisService.deleteColis("COLIS_ID"));
        assertEquals("aucune colis avec id : COLIS_ID",exception.getMessage());
    }

    @Test
    void updateColisStatus_shouldSucceed_andCreateHistoriqueEntry() {
        ColisStatus newStatus = ColisStatus.livrer;
        colisEntity.setId(colisId);
        colisEntity.setStatus(ColisStatus.creer);

        HistoriqueLivraison mockHistorique = new HistoriqueLivraison();
        mockHistorique.setId("h-id");
        ArgumentCaptor<HistoriqueLivraisonRequestDTO> captor = ArgumentCaptor.forClass(HistoriqueLivraisonRequestDTO.class);

        when(colisRepository.findById(eq(colisId))).thenReturn(Optional.of(colisEntity));
        when(colisRepository.save(any(Colis.class))).thenReturn(colisEntity);
        when(historiqueLivraisonMapper.toEntity(captor.capture())).thenReturn(mockHistorique);
        when(historiqueLivraisonRepository.save(eq(mockHistorique))).thenReturn(mockHistorique);
        when(colisMapper.toDTO(eq(colisEntity))).thenReturn(colisResponseDTO);

        // Execute
        ColisResponseDTO result = colisService.updateColisStatus(colisId, newStatus);

        // Verify Colis status updated and saved
        assertNotNull(result);
        assertEquals(newStatus, colisEntity.getStatus());
        verify(colisRepository, times(1)).save(colisEntity);

        // Verify Historique Request DTO content (Historique setup)
        HistoriqueLivraisonRequestDTO capturedDTO = captor.getValue();
        assertEquals(colisId, capturedDTO.getColisId());
        assertEquals(newStatus, capturedDTO.getColisStatus());
        assertTrue(capturedDTO.getCommentaire().contains("livrer"));
        assertEquals(LocalDate.now(), capturedDTO.getDateChangement());

        // Verify Historique saved and added to Colis history
        verify(historiqueLivraisonRepository, times(1)).save(mockHistorique);
        assertTrue(colisEntity.getHistoriqueLivraison().contains(mockHistorique));
    }

    @Test
    void updateColisStatus_WithNullId_ShouldThrowException(){
        assertThrows(InvalidDataException.class,()-> colisService.updateColisStatus(null,ColisStatus.livrer));
    }

    @Test
    void updateColisStatus_WithInvalidId_ShouldThrowException(){
        when(colisRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                ()-> colisService.updateColisStatus(colisId,ColisStatus.livrer));
    }

    @Test
    void updateColisStatus_ShouldThrowExceptionWhenStatusIsTheSame(){
        when(colisRepository.findById(colisId)).thenReturn(Optional.of(colisEntity));

        assertThrows(InvalidDataException.class,
                ()-> colisService.updateColisStatus(colisId,colisEntity.getStatus()));

        verify(colisRepository,times(1)).findById(colisId);
    }
}