package com.app.api.mapper;

import com.app.api.dto.zoneDTO.ZoneRequestDTO;
import com.app.api.dto.zoneDTO.ZoneResponseDTO;
import com.app.api.entity.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    Zone toEntity(ZoneRequestDTO zoneRequestDTO);
    ZoneResponseDTO toDTO(Zone zone);
}
