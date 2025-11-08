package com.app.api.dto.filterDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    Integer page;
    Integer size;
    String status;
    String priority;
    String ville;
    String zone;
}
