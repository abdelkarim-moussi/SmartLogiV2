package com.app.api.integration.service;

import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.dto.destinataireDTO.DestinataireRequestDTO;
import com.app.api.entity.ClientExpediteur;
import com.app.api.entity.Colis;
import com.app.api.enums.ColisPriority;
import com.app.api.repository.ClientExpediteurRepository;
import com.app.api.repository.ColisRepository;
import com.app.api.repository.DestinataireRepository;
import com.app.api.service.ColisService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestPropertySource(
        locations = "classpath:application-test.properties")
class ColisServiceIntegrationTest {

    @Autowired
    private ColisService colisService;

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private ClientExpediteurRepository clientExpediteurRepository;

    @Autowired
    private DestinataireRepository destinataireRepository;

    @Test
    void createColis_WithRealDatabase_ShouldPersistAllEntities() {
        // Given
        ClientExpediteur expediteur = clientExpediteurRepository.save(new ClientExpediteur());
        DestinataireRequestDTO destinataire = new DestinataireRequestDTO("John","Doe","0654124521","john@test.com","california");

        ColisRequestDTO request = ColisRequestDTO.builder()
                .clientExpediteurId(expediteur.getId())
                .poids(2.5F)
                .description("Test package")
                .destinataire(destinataire)
                .codePostal("12345")
                .villeDestination("kech")
                .priority(ColisPriority.standard)
                .build();

        // When
        ColisResponseDTO result = colisService.createColis(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();

        // Verify database persistence
        Colis savedColis = colisRepository.findById(result.getId()).orElseThrow();
        assertThat(savedColis.getClientExpediteur().getId())
                .isEqualTo(expediteur.getId());
        assertThat(savedColis.getDestinataire()).isNotNull();
        assertThat(savedColis.getZone()).isNotNull();
    }


}
