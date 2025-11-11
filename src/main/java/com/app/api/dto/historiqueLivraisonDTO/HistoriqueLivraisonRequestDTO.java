package com.app.api.dto.historiqueLivraisonDTO;
import com.app.api.enums.ColisStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoriqueLivraisonRequestDTO {
    private ColisStatus colisStatus;
    private LocalDate dateChangement;
    private String commentaire;
    private String colisId;
}
