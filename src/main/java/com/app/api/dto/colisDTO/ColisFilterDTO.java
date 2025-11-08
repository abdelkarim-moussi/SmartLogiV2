package com.app.api.dto.colisDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisFilterDTO {
    private String status;
    private String priority;
    private String ville;
    private String search;
    private String zone;
}
