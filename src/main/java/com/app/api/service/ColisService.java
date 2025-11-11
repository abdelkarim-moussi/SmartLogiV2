package com.app.api.service;

import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.dto.colisDTO.ColisResponseDTO;
import com.app.api.dto.historiqueLivraisonDTO.HistoriqueLivraisonRequestDTO;
import com.app.api.entity.*;
import com.app.api.enums.ColisStatus;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.mapper.*;
import com.app.api.repository.*;
import com.app.api.specification.ColisSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class ColisService {
    private final LivreurRepository livreurRepository;
    private final ClientExpediteurRepository clientExpediteurRepository;
    private final DestinataireRepository destinataireRepository;
    private final ZoneRepository zoneRepository;
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final DestinataireMapper destinataireMapper;
    private final ZoneMapper zoneMapper;
    private final ColisProduitRepository colisProduitRepository;
    private ColisRepository colisRepository;
    private ColisMapper colisMapper;
    private HistoriqueLivraisonRepository historiqueLivraisonRepository;
    private HistoriqueLivraisonMapper historiqueLivraisonMapper;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("status","priority");

    public ColisService(ColisRepository colisRepository,
                        ColisMapper colisMapper,
                        LivreurRepository livreurRepository,
                        ClientExpediteurRepository clientExpediteurRepository,
                        DestinataireRepository destinataireRepository,
                        ZoneRepository zoneRepository,
                        ProduitRepository produitRepository,
                        ProduitMapper produitMapper,
                        DestinataireMapper destinataireMapper,
                        ZoneMapper zoneMapper,
                        ColisProduitRepository colisProduitRepository,
                        HistoriqueLivraisonRepository historiqueLivraisonRepository,
                        HistoriqueLivraisonMapper historiqueLivraisonMapper
    ){

        this.colisRepository = colisRepository;
        this.colisMapper = colisMapper;
        this.livreurRepository = livreurRepository;
        this.clientExpediteurRepository = clientExpediteurRepository;
        this.destinataireRepository = destinataireRepository;
        this.zoneRepository = zoneRepository;
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.destinataireMapper = destinataireMapper;
        this.zoneMapper = zoneMapper;
        this.colisProduitRepository = colisProduitRepository;
        this.historiqueLivraisonRepository = historiqueLivraisonRepository;
        this.historiqueLivraisonMapper = historiqueLivraisonMapper;
    }

    public ColisResponseDTO createColis(ColisRequestDTO colisRequestDTO){

        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide");
        }

        Colis colis = colisMapper.toEntity(colisRequestDTO);
        setRelations(colisRequestDTO,colis);
        Colis createdColis = colisRepository.save(colis);
        setProduits(colisRequestDTO,createdColis);
        Colis finalSavedColis = colisRepository.save(createdColis);

        return colisMapper.toDTO(finalSavedColis);

    }

    public ColisResponseDTO updateColis(String id,ColisRequestDTO colisRequestDTO){

        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalid id : "+id);
        }
        if(colisRequestDTO == null){
            throw new InvalidDataException("données invalide "+null);
        }

        Colis existingColis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis trouver avec id : "+id)
        );

        existingColis.setPoids(colisRequestDTO.getPoids());
        existingColis.setDescription(colisRequestDTO.getDescription());
        existingColis.setPriority(colisRequestDTO.getPriority());
        existingColis.setStatus(colisRequestDTO.getStatus());
        existingColis.setVilleDestination(colisRequestDTO.getVilleDestination());

        setRelations(colisRequestDTO,existingColis);
        setProduits(colisRequestDTO,existingColis);
        Colis updatedColis = colisRepository.save(existingColis);

        return colisMapper.toDTO(updatedColis);
    }

    public Page<ColisResponseDTO> getAllColis(ColisFilterDTO filters ,
                                              Integer page,
                                              Integer size,
                                              String sortBy,
                                              String sortDir){
        if(!ALLOWED_SORT_FIELDS.contains(sortBy)){
            sortBy = "status";
        }

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Specification<Colis> specification = ColisSpecification.buildColisSpecification(filters);

        return colisRepository.findAll(specification,pageable)
                .map(colisMapper::toDTO);
    }

    public ColisResponseDTO getOneColisById(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invcalide id :"+id);
        }

        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis disponible avec cet id : "+id)
        );

        return colisMapper.toDTO(colis);

    }

    public void deleteColis(String id){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalide id = "+id);
        }
        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis avec id = "+id)
        );

        colisRepository.deleteById(id);
    }

    public ColisResponseDTO updateColisStatus(String id,ColisStatus colisStatus){
        if(id == null || id.trim().isEmpty()){
            throw new InvalidDataException("invalide id = "+id);
        }

        Colis colis = colisRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("aucune colis avec id = "+id)
        );

        if(colis.getStatus().equals(colisStatus)){
            log.error("la colis est déja on status : {}", colisStatus);
                throw new InvalidDataException("la colis est déja on status : "+colisStatus);
        }
        colis.setStatus(colisStatus);
        colisRepository.save(colis);

        HistoriqueLivraisonRequestDTO historiqueLivraisonRequestDTO = HistoriqueLivraisonRequestDTO.builder()
                .colisId(colis.getId())
                .colisStatus(colisStatus)
                .dateChangement(LocalDate.now())
                .commentaire("le status de la colis est changer ver "+colisStatus)
                .build();

        HistoriqueLivraison historiqueLivraison = historiqueLivraisonRepository.save(historiqueLivraisonMapper.toEntity(historiqueLivraisonRequestDTO));
        colis.getHistoriqueLivraison().add(historiqueLivraison);
        return colisMapper.toDTO(colis);
    }

    private void setRelations(ColisRequestDTO colisRequestDTO, Colis colis){

        if(colisRequestDTO.getClientExpediteurId() != null){
            ClientExpediteur expediteur = clientExpediteurRepository.findById(colisRequestDTO.getClientExpediteurId()).orElseThrow(
                    () -> new ResourceNotFoundException("aucun expediteur trouver avec cet id = "+colisRequestDTO.getClientExpediteurId())
            );
            colis.setClientExpediteur(expediteur);
        }

        if(colisRequestDTO.getDestinataire() != null){
            Destinataire requestDestinataire = destinataireMapper.toEntity(colisRequestDTO.getDestinataire());
            Destinataire destinataire;
            if(requestDestinataire.getEmail() != null && !requestDestinataire.getEmail().trim().isEmpty()){
                destinataire = destinataireRepository.findByEmail(requestDestinataire.getEmail()).orElseGet(
                    () -> destinataireRepository.save(requestDestinataire)
                );
            }
            else {
                destinataire = destinataireRepository.save(requestDestinataire);
            }

            colis.setDestinataire(destinataire);
        }

        if(colisRequestDTO.getCodePostal() != null){
            Zone zone = zoneRepository.findByCodePostal(colisRequestDTO.getCodePostal())
                    .orElseGet(() -> zoneRepository.save(new Zone(colisRequestDTO.getCodePostal())));
            colis.setZone(zone);
        }
    }

    private void setProduits(ColisRequestDTO colisRequestDTO, Colis colis){
        Set<ColisProduit> colisProduits = new HashSet<>();
        if(colisRequestDTO.getProduits() != null){
            colisRequestDTO.getProduits().forEach(produit ->
            {
                Produit requestProduit = new Produit();
                requestProduit.setNom(produit.getNom());
                requestProduit.setPrix(produit.getPrix());
                requestProduit.setCategorie(produit.getCategorie());
                requestProduit.setPoids(produit.getPoids());

                Produit savedProduit = produitRepository.save(requestProduit);

                ColisProduitKey colisProduitId = new ColisProduitKey();
                colisProduitId.setColisId(colis.getId());
                colisProduitId.setProduitId(savedProduit.getId());

                ColisProduit colisProduit = new ColisProduit();
                colisProduit.setId(colisProduitId);
                colisProduit.setColis(colis);
                colisProduit.setProduit(savedProduit);
                colisProduit.setQuantite(produit.getQuantite());
                colisProduit.setPrix(savedProduit.getPrix());

                ColisProduit savedColisProduit = colisProduitRepository.save(colisProduit);

                savedColisProduit.setProduit(savedProduit);
                savedColisProduit.setColis(colis);

                colisProduits.add(savedColisProduit);
            });

        }
            colis.setColisProduits(colisProduits);
    }
}
