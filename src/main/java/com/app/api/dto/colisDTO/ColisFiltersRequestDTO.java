package com.app.api.dto.colisDTO;

import lombok.Data;

@Data
public class ColisFiltersRequestDTO {

    private int size = 0;
    private int page = 10;
    private String sortBy = "status";
    private String sortDirection = "DESC";

    private String status;
    private String priority;
    private String LivreurId;
    private String expediteurId;
    private String ville;
    private String search;
    private String zone;
}
