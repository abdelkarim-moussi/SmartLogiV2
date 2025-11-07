package com.app.api.dto.historiqueLivraisonDTO;

import com.app.api.entity.Colis;
import com.app.api.enums.ColisStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class HistoriqueLivraisonRequestDTO {
    private String nom;
    private ColisStatus colisStatus;
    private LocalDate dateChangement;
    private String commentaire;
    private String colisId;
}
