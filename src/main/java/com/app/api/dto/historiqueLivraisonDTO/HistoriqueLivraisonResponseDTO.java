package com.app.api.dto.historiqueLivraisonDTO;
import com.app.api.enums.ColisStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class HistoriqueLivraisonResponseDTO {
    private String id;
    private String colidId;
    private ColisStatus colisStatus;
    private LocalDate dateChangement;
    private String commentaire;
}
